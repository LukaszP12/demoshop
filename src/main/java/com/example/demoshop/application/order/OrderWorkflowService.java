package com.example.demoshop.application.order;

import com.example.demoshop.application.shipping.ShippingEventPublisher;
import com.example.demoshop.application.shipping.ShippingRoutingKey;
import com.example.demoshop.application.shipping.ShippingService;

import com.example.demoshop.domain.event.OrderCancelledEvent;
import com.example.demoshop.domain.event.OrderDeliveredEvent;
import com.example.demoshop.domain.event.OrderPaidEvent;
import com.example.demoshop.domain.event.OrderPlacedEvent;
import com.example.demoshop.domain.event.OrderReturnedEvent;
import com.example.demoshop.domain.event.OrderShippedEvent;
import com.example.demoshop.domain.event.PaymentSuccessfulEvent;
import com.example.demoshop.domain.event.ShipmentCreatedEvent;

import com.example.demoshop.domain.model.cart.Cart;
import com.example.demoshop.domain.model.catalogue.Product;
import com.example.demoshop.domain.model.order.Order;
import com.example.demoshop.domain.model.shipping.Shipment;
import com.example.demoshop.domain.model.user.Address;
import com.example.demoshop.domain.model.user.User;

import com.example.demoshop.domain.repository.CartRepository;
import com.example.demoshop.domain.repository.OrderRepository;
import com.example.demoshop.domain.repository.ProductRepository;
import com.example.demoshop.domain.repository.ShipmentRepository;
import com.example.demoshop.domain.repository.UserRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class OrderWorkflowService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ShippingService shippingService;
    private final ShipmentRepository shipmentRepository;
    private final main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final main.java.com.example.demoshop.java.com.example.demoshop.application.order.OrderEventPublisher eventPublisher;
    private final ShippingEventPublisher shippingEventPublisher;

    public OrderWorkflowService(OrderRepository orderRepository,
                                CartRepository cartRepository,
                                ProductRepository productRepository,
                                ShippingService shippingService,
                                ShipmentRepository shipmentRepository,
                                main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.CouponRepository couponRepository,
                                @Qualifier("postgresUserRepository") UserRepository userRepository,
                                main.java.com.example.demoshop.java.com.example.demoshop.application.order.OrderEventPublisher orderEventPublisher,
                                ShippingEventPublisher shippingEventPublisher) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.shippingService = shippingService;
        this.shipmentRepository = shipmentRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
        this.eventPublisher = orderEventPublisher;
        this.shippingEventPublisher = shippingEventPublisher;
    }

    /**
     * Reacts to PaymentSuccessfulEvent after transaction commit.
     */
    @TransactionalEventListener
    public void handlePaymentSuccess(PaymentSuccessfulEvent event, Address shippingAddress) {
        Order order = orderRepository.findById(event.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + event.orderId()));

        order.markPaid();
        orderRepository.save(order);

        Shipment shipment = shippingService.createShipment(event.orderId(), shippingAddress);
        shipmentRepository.save(shipment);
        shippingEventPublisher.publish(new ShipmentCreatedEvent(shipment.id(), order.getId()), ShippingRoutingKey.SHIPMENT_CREATED);

        order.markShipped();
        orderRepository.save(order);
    }

    public Order createOrderFromCart(String userId, String couponCode) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart of userId: " + userId + " not found or expired: "));

        if (cart.items().isEmpty()) {
            throw new IllegalArgumentException("Cannot create order from empty cart");
        }

        List<main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.OrderItem> orderItems = cart.items().stream()
                .map(cartItem -> new main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.OrderItem(
                        cartItem.productId().value(),
                        cartItem.quantity(),
                        cartItem.unitPrice()))
                .toList();

        for (main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.OrderItem orderItem : orderItems) {
            Product product = productRepository.findById(orderItem.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Product with id: " + orderItem.productId() + " not found: "));

            synchronized (product) {
                if (product.getStockQuantity() < orderItem.getQuantity()) {
                    throw new IllegalStateException("Not enough stock for product " + product.getName());
                }
                product.decreaseStock(orderItem.getQuantity());
                productRepository.save(product);
            }
        }

        BigDecimal total = BigDecimal.ZERO;

        for (main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.OrderItem i : orderItems) {
            main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money lineTotal = i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity()));
            total = total.add(lineTotal.getAmount());
        }

        main.java.com.example.demoshop.java.com.example.demoshop.domain.model.coupon.Coupon coupon = null;
        if (couponCode != null && !couponCode.isBlank()) {
            coupon = couponRepository.findByCode(couponCode)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid coupon code"));

            if (!coupon.isValid()) {
                throw new IllegalStateException("Coupon expired or usage limit reached");
            }

            BigDecimal discount = coupon.isPercentage()
                    ? total.multiply(coupon.getDiscountValue().divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP))
                    : coupon.getDiscountValue();

            total = total.subtract(discount);
            if (total.compareTo(BigDecimal.ZERO) < 0) {
                total = BigDecimal.ZERO;
            }

            coupon.incrementUsage();
            couponRepository.save(coupon);
        }

        String currency = orderItems.get(0).getUnitPrice().getCurrency();

        Order order = new Order(userId, orderItems, total, currency, coupon);
        orderRepository.save(order);

        eventPublisher.publish(new OrderPlacedEvent(order.getId()),
                "order.placed");

        cartRepository.clearCart(userId);

        return order;
    }

    public Order payOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        PaymentSuccessfulEvent paymentEvent = new PaymentSuccessfulEvent(orderId, main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money.of(order.getTotal(),
                order.getCurrency()));

        eventPublisher.publish(new OrderPaidEvent(order.getId()),
                "order.paid");

        order.markPaid();

        User user = userRepository.findById(order.getUserId()).orElseThrow(() -> new main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.UserNotExistsException(order.getUserId()));
        user.earnPointsFromOrder(order, new main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.LoyaltyPolicy(BigDecimal.TEN));

        return orderRepository.save(order);
    }

    public Order shipOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        shippingService.shipOrder(order);

        OrderShippedEvent orderShippedEvent = new OrderShippedEvent(orderId, Instant.now());
        order.markShipped();

        eventPublisher.publish(orderShippedEvent, "order.ship");

        return orderRepository.save(order);
    }

    public Order deliverOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderDeliveredEvent deliveredEvent = new OrderDeliveredEvent(orderId);
        order.markDelivered();

        eventPublisher.publish(deliveredEvent, "order.delivered");

        return orderRepository.save(order);
    }

    public Order requestReturn(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderReturnedEvent returnedEvent = new OrderReturnedEvent(orderId);
        order.markReturned();

        eventPublisher.publish(returnedEvent, "order.return-requested");

        return orderRepository.save(order);
    }

    public Order cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        eventPublisher.publish(new OrderCancelledEvent(order.getId(), Instant.now()), "order.cancel");

        order.cancel();
        return orderRepository.save(order);
    }

    public List<Order> listCustomerOrders(String customerId) {
        return orderRepository.findByUserId(customerId);
    }

    public void markPaid(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.markPaid();
        orderRepository.save(order);
    }

    public Order markShipped(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.markShipped();
        return orderRepository.save(order);
    }

    public Order markDelivered(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.markDelivered();
        return orderRepository.save(order);
    }

    public Order markReturned(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.markReturned();
        return orderRepository.save(order);
    }
}

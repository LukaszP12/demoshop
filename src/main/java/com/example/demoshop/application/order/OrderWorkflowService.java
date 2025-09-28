package main.java.com.example.demoshop.java.com.example.demoshop.application.order;


import main.java.com.example.demoshop.java.com.example.demoshop.application.shipping.ShippingService;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.OrderCancelledEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.OrderDeliveredEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.OrderPaidEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.OrderPlacedEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.OrderReturnedEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.OrderShippedEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.PaymentSuccessfulEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.Cart;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Product;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.coupon.Coupon;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.OrderItem;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.Shipment;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.Address;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.CartRepository;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.CouponRepository;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.OrderRepository;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

@Service
public class OrderWorkflowService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ShippingService shippingService;
    private final CouponRepository couponRepository;
    private final OrderEventPublisher eventPublisher;

    public OrderWorkflowService(OrderRepository orderRepository,
                                CartRepository cartRepository,
                                ProductRepository productRepository,
                                ShippingService shippingService,
                                CouponRepository couponRepository,
                                OrderEventPublisher orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.shippingService = shippingService;
        this.couponRepository = couponRepository;
        this.eventPublisher = orderEventPublisher;
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

        order.markShipped();
        orderRepository.save(order);
    }

    public Order createOrderFromCart(String userId, String couponCode) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with id: " + userId + "not found"));

        if (cart.items().isEmpty()) {
            throw new IllegalArgumentException("Cannot create order from empty cart");
        }

        List<OrderItem> orderItems = cart.items().stream()
                .map(cartItem -> new OrderItem(
                        cartItem.productId().value(),
                        cartItem.quantity(),
                        cartItem.unitPrice()))
                .toList();

        for (OrderItem orderItem : orderItems) {
            Product product = productRepository.findById(orderItem.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Product with id: " + orderItem.productId() + " not found: "));

            synchronized (product) {
                if (product.getStock() < orderItem.getQuantity()) {
                    throw new IllegalStateException("Not enough stock for product " + product.getName());
                }
                product.decreaseStock(orderItem.getQuantity());
                productRepository.save(product);
            }
        }

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem i : orderItems) {
            Money lineTotal = i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity()));
            total = total.add(lineTotal.getAmount());
        }

        Coupon coupon = null;
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

        eventPublisher.publish(new OrderPlacedEvent(order.getId(), order.getUserId()));

        cartRepository.clearCart(userId);

        return order;
    }

    public Order payOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        PaymentSuccessfulEvent paymentEvent = new PaymentSuccessfulEvent(orderId, Money.of(order.getTotal(),
                order.getCurrency()));


        eventPublisher.publish(new OrderPaidEvent(order.getId(), order.getUserId()));

        order.markPaid();
        return orderRepository.save(order);
    }

    public Order shipOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        shippingService.shipOrder(order);

        OrderShippedEvent orderShippedEvent = new OrderShippedEvent(orderId, Instant.now());
        order.markShipped();

        eventPublisher.publish(orderShippedEvent);

        return orderRepository.save(order);
    }

    public Order deliverOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderDeliveredEvent deliveredEvent = new OrderDeliveredEvent(orderId);
        order.markDelivered();

        eventPublisher.publish(deliveredEvent);

        return orderRepository.save(order);
    }

    public Order requestReturn(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderReturnedEvent returnedEvent = new OrderReturnedEvent(orderId);
        order.markReturned();

        eventPublisher.publish(returnedEvent);

        return orderRepository.save(order);
    }

    public Order cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        eventPublisher.publish(new OrderCancelledEvent(order.getId(), Instant.now()));

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

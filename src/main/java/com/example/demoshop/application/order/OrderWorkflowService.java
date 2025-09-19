package main.java.com.example.demoshop.java.com.example.demoshop.application.order;


import main.java.com.example.demoshop.java.com.example.demoshop.application.shipping.ShippingService;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.OrderDeliveredEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.OrderReturnedEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.OrderShippedEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.PaymentSuccessfulEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.Cart;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Product;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.OrderItem;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.Shipment;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.Address;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.CartRepository;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.OrderRepository;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Service
public class OrderWorkflowService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ShippingService shippingService;

    public OrderWorkflowService(OrderRepository orderRepository,
                                CartRepository cartRepository,
                                ProductRepository productRepository,
                                ShippingService shippingService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.shippingService = shippingService;
    }

    /**
     * Reacts to PaymentSuccessfulEvent after transaction commit.
     */
    @TransactionalEventListener
    public void handlePaymentSuccess(PaymentSuccessfulEvent event, Address shippingAddress) {
        Order order = orderRepository.findById(new Order.OrderId(event.orderId().value()))
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + event.orderId()));

        order.markPaid(event);
        orderRepository.save(order);

        Shipment shipment = shippingService.createShipment(event.orderId().value(), shippingAddress);

        order.markShipped();
        orderRepository.save(order);
    }

    public Order createOrderFromCart(String userId) {
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
                if (product.getStock() < orderItem.quantity()) {
                    throw new IllegalStateException("Not enough stock for product " + product.getName());
                }
                product.decreaseStock(orderItem.quantity());
                productRepository.save(product);
            }
        }

        Order order = new Order(cart.userId(),
                orderItems);

        orderRepository.save(order);
        return order;
    }

    public Order payOrder(String orderId) {
        Order order = orderRepository.findById(new Order.OrderId(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        PaymentSuccessfulEvent paymentEvent = new PaymentSuccessfulEvent(new Order.OrderId(orderId), order.total());

        order.markPaid(paymentEvent);
        return orderRepository.save(order);
    }

    public Order shipOrder(String orderId) {
        Order order = orderRepository.findById(new Order.OrderId(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderShippedEvent orderShippedEvent = new OrderShippedEvent(orderId);
        order.markShipped(orderShippedEvent);

        return orderRepository.save(order);
    }

    public Order deliverOrder(String orderId) {
        Order order = orderRepository.findById(new Order.OrderId(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderDeliveredEvent deliveredEvent = new OrderDeliveredEvent(orderId);
        order.markDelivered(deliveredEvent);

        return orderRepository.save(order);
    }

    public Order requestReturn(String orderId) {
        Order order = orderRepository.findById(new Order.OrderId(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderReturnedEvent returnedEvent = new OrderReturnedEvent(orderId);
        order.markReturned(returnedEvent);

        return orderRepository.save(order);
    }

    public Order cancelOrder(String orderId) {
        Order order = orderRepository.findById(new Order.OrderId(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.cancel();
        return orderRepository.save(order);
    }

    public List<Order> listCustomerOrders(String customerId) {
        return orderRepository.findByUserId(customerId);
    }
}

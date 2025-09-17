package main.java.com.example.demoshop.java.com.example.demoshop.application.order;


import main.java.com.example.demoshop.java.com.example.demoshop.application.shipping.ShippingService;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.PaymentSuccessfulEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.Cart;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.OrderItem;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.Shipment;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.Address;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Service
public class OrderWorkflowService {

    private final OrderRepository orderRepository;
    private final ShippingService shippingService;

    public OrderWorkflowService(OrderRepository orderRepository,
                                ShippingService shippingService) {
        this.orderRepository = orderRepository;
        this.shippingService = shippingService;
    }

    /**
     * Reacts to PaymentSuccessfulEvent after transaction commit.
     */
    @TransactionalEventListener
    public void handlePaymentSuccess(PaymentSuccessfulEvent event, Address shippingAddress) {
        Order order = orderRepository.findById(new Order.OrderId(event.orderId()))
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + event.orderId()));

        order.markPaid(event);
        orderRepository.save(order);

        Shipment shipment = shippingService.createShipment(event.orderId(), shippingAddress);

        order.markShipped();
        orderRepository.save(order);
    }

    public Order createOrderFromCart(Cart cart) {
        if (cart.items().isEmpty()){
            throw new IllegalArgumentException("Cannot create order from empty cart");
        }

        List<OrderItem> orderItems = cart.items().stream()
                .map(item -> new OrderItem(item.productId().value(), item.quantity(), item.unitPrice()))
                .toList();

        Order order = new Order(cart.userId(),
                orderItems);

        orderRepository.save(order);

        return order;
    }
}

package main.java.com.example.demoshop.java.com.example.demoshop.application.order;


import main.java.com.example.demoshop.java.com.example.demoshop.application.shipping.ShippingService;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.PaymentSuccessfulEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.Shipment;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.Address;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

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
    public void handlePaymentSuccess(PaymentSuccessfulEvent event) {
        // Load the order
        Order order = orderRepository.findById(new Order.OrderId(event.orderId()))
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + event.orderId()));

        // Mark order as paid
        order.markPaid(event);
        orderRepository.save(order);

        // Create a shipment
        Address shippingAddress = order.getShippingAddress(); // assuming order holds address
        Shipment shipment = shippingService.createShipment(order.id().value(), shippingAddress);

        // Mark order as shipped
        order.markShipped();
        orderRepository.save(order);

        // Optionally, we could publish OrderShippedEvent here
        // applicationEventPublisher.publishEvent(new OrderShippedEvent(...));
    }
}

package example.demoshop.infrastructure.messaging;

import example.demoshop.domain.event.OrderCancelledEvent;
import example.demoshop.domain.event.OrderDeliveredEvent;
import example.demoshop.domain.event.OrderPaidEvent;
import example.demoshop.domain.event.OrderPlacedEvent;
import example.demoshop.domain.event.OrderReturnRequestedEvent;
import example.demoshop.domain.event.OrderShippedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEventHandler {

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE_NAME = "events-exchange";

    public OrderEventHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderPlaced(OrderPlacedEvent event) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "order.placed", event);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderPaid(OrderPaidEvent event) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "order.paid", event);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderShipped(OrderShippedEvent event) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "order.shipped", event);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderDelivered(OrderDeliveredEvent event) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "order.delivered", event);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderReturnRequested(OrderReturnRequestedEvent event) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "order.return-requested", event);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCancelled(OrderCancelledEvent event) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "order.cancelled", event);
    }
}

package main.java.com.example.demoshop.java.com.example.demoshop.infrastructure.messaging;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.OrderPlacedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEventHandler {

    private final RabbitTemplate rabbitTemplate;

    public OrderEventHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderPlacedEvent event){
        rabbitTemplate.convertAndSend("events-exchange","order.placed",event);
    }
}

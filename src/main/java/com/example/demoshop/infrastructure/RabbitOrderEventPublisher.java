package main.java.com.example.demoshop.java.com.example.demoshop.infrastructure;

import main.java.com.example.demoshop.java.com.example.demoshop.application.order.OrderEventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitOrderEventPublisher implements OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitOrderEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(Object event,String routingKey) {
        rabbitTemplate.convertAndSend("events-exchange",routingKey,event);
    }
}

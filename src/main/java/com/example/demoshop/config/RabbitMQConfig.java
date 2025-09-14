package main.java.com.example.demoshop.java.com.example.demoshop.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "events-exchange";

    // --- Queues ---
    public static final String COUPON_QUEUE = "coupon-events";
    public static final String ORDER_QUEUE = "order-events";
    public static final String REVIEW_QUEUE = "review-events";

    @Bean
    public DirectExchange eventExchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue couponQueue() {
        return new Queue(COUPON_QUEUE, true);
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE, true);
    }

    @Bean
    public Queue reviewQueue() {
        return new Queue(REVIEW_QUEUE, true);
    }

    // --- Bindings ---
    @Bean
    public Binding couponBinding(Queue couponQueue, DirectExchange eventExchange) {
        return BindingBuilder.bind(couponQueue).to(eventExchange).with("coupon.used");
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, DirectExchange eventExchange) {
        return BindingBuilder.bind(orderQueue).to(eventExchange).with("order.placed");
    }

    @Bean
    public Binding reviewBinding(Queue reviewQueue, DirectExchange eventExchange) {
        return BindingBuilder.bind(reviewQueue).to(eventExchange).with("review.created");
    }
}

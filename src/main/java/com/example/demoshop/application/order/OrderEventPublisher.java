package main.java.com.example.demoshop.java.com.example.demoshop.application.order;

public interface OrderEventPublisher {
    <T> void publish(T event,String routingKey);
}

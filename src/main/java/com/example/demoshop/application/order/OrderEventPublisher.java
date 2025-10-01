package main.java.com.example.demoshop.java.com.example.demoshop.application.order;

public interface OrderEventPublisher {
    void publish(Object event,String routingKey);
}

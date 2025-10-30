package example.demoshop.application.order;

public interface OrderEventPublisher {
    <T> void publish(T event,String routingKey);
}

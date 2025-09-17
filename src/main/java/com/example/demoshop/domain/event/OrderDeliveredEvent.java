package main.java.com.example.demoshop.java.com.example.demoshop.domain.event;

public class OrderDeliveredEvent {
    private final String orderId;

    public OrderDeliveredEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId(){
        return orderId;
    }
}

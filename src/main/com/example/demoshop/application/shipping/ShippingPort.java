package example.demoshop.application.shipping;


import example.demoshop.domain.model.order.Order;

public interface ShippingPort {
    void shipOrder(Order order);
}

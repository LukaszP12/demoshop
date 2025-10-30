package example.demoshop.application.order;

import example.demoshop.domain.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderReadService {
    Page<Order> listOrders(String userId, Order.OrderStatus status, Pageable pageable);

    Order getOrderById(String orderId);
}

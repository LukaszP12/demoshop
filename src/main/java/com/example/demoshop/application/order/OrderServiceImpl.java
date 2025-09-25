package main.java.com.example.demoshop.java.com.example.demoshop.application.order;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import main.java.com.example.demoshop.java.com.example.demoshop.infrastructure.repository.SpringDataOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

    private final SpringDataOrderRepository orderRepository;

    public OrderServiceImpl(SpringDataOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Page<Order> listOrders(String userId, Order.OrderStatus status, Pageable pageable) {
        if (userId != null && status != null) {
            return orderRepository.findByUserIdAndStatus(userId, status, pageable);
        } else if (userId != null) {
            return orderRepository.findByUserId(userId, pageable);
        } else if (status != null) {
            return orderRepository.findByStatus(status, pageable);
        } else {
            return orderRepository.findAll(pageable);
        }
    }

    @Override
    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order with id: " + orderId + " not found. "));
    }
}

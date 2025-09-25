package main.java.com.example.demoshop.java.com.example.demoshop.application.order;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderFacade {

    private final OrderWorkflowService workflowService;
    private final OrderService orderService;

    public OrderFacade(OrderWorkflowService workflowService, OrderService orderService) {
        this.workflowService = workflowService;
        this.orderService = orderService;
    }

    public Order placeOrder(String userId, String couponCode) {
        return workflowService.createOrderFromCart(userId, couponCode);
    }

    public Order cancelOrder(String orderId) {
        return workflowService.cancelOrder(orderId);
    }

    public void markPaid(String orderId) {
        workflowService.markPaid(orderId);
    }

    public Order markShipped(String orderId) {
        return workflowService.markShipped(orderId);
    }

    public Order markDelivered(String orderId) {
        return workflowService.markDelivered(orderId);
    }

    public Order markReturned(String orderId) {
        return workflowService.markReturned(orderId);
    }

    public Page<Order> listOrders(String userId, Order.OrderStatus status, Pageable pageable) {
        return orderService.listOrders(userId, status, pageable);
    }

    public Order getOrderById(String orderId){
        return orderService.getOrderById(orderId);
    }
}

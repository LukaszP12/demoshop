package main.java.com.example.demoshop.java.com.example.demoshop.presentation.order;

import main.java.com.example.demoshop.java.com.example.demoshop.application.order.OrderWorkflowService;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderWorkflowService orderService;

    public OrderController(OrderWorkflowService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestParam String userId,
                                            @RequestParam(required = false) String couponCode) {
        try {
            Order order = orderService.createOrderFromCart(userId, couponCode);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<Order> payOrder(@PathVariable String orderId) {
        Order updatedOrder = orderService.payOrder(orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/{orderId}/ship")
    public ResponseEntity<Order> shipOrder(@PathVariable String orderId) {
        Order updatedOrder = orderService.shipOrder(orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/{orderId}/deliver")
    public ResponseEntity<Order> deliverOrder(@PathVariable String orderId) {
        Order updatedOrder = orderService.deliverOrder(orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/{orderId}/return")
    public ResponseEntity<Order> requestReturn(@PathVariable String orderId) {
        Order updatedOrder = orderService.requestReturn(orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Order> cancelOrder(@PathVariable String orderId) {
        Order updatedOrder = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(updatedOrder);
    }
}

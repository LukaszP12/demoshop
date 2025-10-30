package example.demoshop.presentation.order;

import example.demoshop.application.order.OrderFacade;
import example.demoshop.domain.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderFacade orderFacade;

    public OrderController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @GetMapping
    public Page<Order> listOrders(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) Order.OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return orderFacade.listOrders(userId, status, pageable);
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestParam String userId,
                                            @RequestParam(required = false) String couponCode) {
        try {
            Order order = orderFacade.placeOrder(userId, couponCode);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<Order> payOrder(@PathVariable String orderId) {
        Order updatedOrder = orderFacade.markDelivered(orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/{orderId}/ship")
    public ResponseEntity<Order> shipOrder(@PathVariable String orderId) {
        Order updatedOrder = orderFacade.markShipped(orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/{orderId}/deliver")
    public ResponseEntity<Order> deliverOrder(@PathVariable String orderId) {
        Order updatedOrder = orderFacade.markDelivered(orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/{orderId}/return")
    public ResponseEntity<Order> requestReturn(@PathVariable String orderId) {
        Order updatedOrder =  orderFacade.markReturned(orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Order> cancelOrder(@PathVariable String orderId) {
        Order updatedOrder = orderFacade.cancelOrder(orderId);
        return ResponseEntity.ok(updatedOrder);
    }
}

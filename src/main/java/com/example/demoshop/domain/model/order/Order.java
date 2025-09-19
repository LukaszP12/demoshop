package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.OrderDeliveredEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.OrderReturnedEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.OrderShippedEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.PaymentSuccessfulEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.coupon.Coupon;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.Address;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Document(collection = "orders")
public class Order {

    private final OrderId id;
    private final String userId;
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.CREATED;
    private final Address shippingAddress;
    private Money total;
    private Coupon appliedCoupon;

    private final Instant createdAt = Instant.now();

    // Constructor for OrderWorkflowService usage
    public Order(String userId, Collection<OrderItem> items) {
        this.id = OrderId.newId(); // generate a new OrderId
        this.userId = userId;
        this.items.addAll(items);
        this.status = OrderStatus.CREATED;
        this.shippingAddress = null; // can be set later or default
        this.total = calculateTotal();
    }

    public Order(OrderId id, String userId, List<OrderItem> items, Address shippingAddress) {
        this.id = id;
        this.userId = userId;
        this.shippingAddress = shippingAddress;
        this.items.addAll(items);
        this.total = calculateTotal();
    }

    private Money calculateTotal() {
        return items.stream()
                .map(OrderItem::subtotal)
                .reduce(Money.zero(), Money::add);
    }

    public void applyCoupon(Coupon coupon) {
        if (!coupon.isValid()) {
            throw new IllegalStateException("Invalid or expired coupon");
        }
        this.appliedCoupon = coupon;
        // Recalculate total with discount
        Money subtotal = calculateTotal(); // sum of OrderItems
        this.total = subtotal.applyDiscount(coupon.getDiscountValue(), coupon.isPercentage());
    }

    public void removeCoupon() {
        this.appliedCoupon = null;
        this.total = calculateTotal();
    }

    public void addItem(OrderItem item) {
        if (item == null) throw new IllegalArgumentException("Item is required");
        if (item.quantity() <= 0) throw new IllegalArgumentException("Quantity must be > 0");
        items.add(item);
        recalculateTotal();
    }

    private void recalculateTotal() {
        Money subtotal = calculateTotal();
        if (appliedCoupon != null && appliedCoupon.isValid()) {
            this.total = new Money(appliedCoupon.apply(subtotal.getAmount()));
        } else {
            this.total = subtotal;
        }
    }

    // --- Order lifecycle ---

    public void place() {
        if (items.isEmpty()) throw new IllegalStateException("Cannot place order without items");
        status = OrderStatus.CREATED;
    }

    public void markPaid(PaymentSuccessfulEvent event) {
        if (this.status != OrderStatus.CREATED) {
            throw new IllegalStateException("Only CREATED orders can be paid.");
        }
        this.status = OrderStatus.PAID;
    }

    public void markShipped(OrderShippedEvent event) {
        if (!this.id.equals(new OrderId(event.orderId()))) {
            throw new IllegalArgumentException("Event does not belong to this order");
        }
        if (this.status != OrderStatus.PAID) {
            throw new IllegalStateException("Only paid orders can be shipped.");
        }
        this.status = OrderStatus.SHIPPED;
    }

    public void markDelivered(OrderDeliveredEvent event) {
        if (!this.id.equals(new OrderId(event.getOrderId()))){
            throw new IllegalArgumentException("Event does not belong to this order");
        }

        if (status != OrderStatus.SHIPPED) {
            throw new IllegalStateException("Only SHIPPED orders can be delivered.");
        }
        this.status = OrderStatus.DELIVERED;
    }

    public void markReturned() {
        if (status != OrderStatus.DELIVERED) {
            throw new IllegalStateException("Only DELIVERED orders can be returned.");
        }
        this.status = OrderStatus.RETURNED;
    }

    public void cancel(Duration allowedDuration) {
        if (Duration.between(createdAt, Instant.now()).compareTo(allowedDuration) > 0) {
            throw new IllegalStateException("Order can no longer be cancelled.");
        }
        if (status != OrderStatus.CREATED && status != OrderStatus.PAID) {
            throw new IllegalStateException("Only CREATED or PAID orders can be cancelled.");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public void requestReturn() {
        if (status != OrderStatus.DELIVERED) {
            throw new IllegalStateException("Only delivered orders can be returned.");
        }
        this.status = OrderStatus.RETURNED;
        // optionally refund
        this.total = Money.zero();
    }

    public void markReturned(OrderReturnedEvent event) {
        if (!this.id.equals(new OrderId(event.getOrderId()))){
            throw new IllegalArgumentException("Event does not belong to this order");
        }
        if (status != OrderStatus.DELIVERED){
            throw new IllegalArgumentException("Only DELIVERED orders can be returned.");
        }
        this.status = OrderStatus.RETURNED;
    }

    public void cancel() {
        if (status == OrderStatus.SHIPPED || status == OrderStatus.DELIVERED || status == OrderStatus.RETURNED) {
            throw new IllegalStateException("Order cannot be cancelled after it has been shipped or delivered.");
        }
        this.status = OrderStatus.CANCELLED;
    }

        // --- Getters ---

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public OrderId id() {
        return id;
    }

    public List<OrderItem> items() {
        return Collections.unmodifiableList(items);
    }

    public OrderStatus status() {
        return status;
    }

    public Money total() {
        return total;
    }

    public Optional<Coupon> appliedCoupon() {
        return Optional.ofNullable(appliedCoupon);
    }

    // --- Types ---

    public enum OrderStatus {CREATED, PAID, SHIPPED, DELIVERED, RETURNED, CANCELLED}

    public static class OrderId {
        private final String value;

        public OrderId(String value) {
            this.value = value;
        }

        public static OrderId newId() {
            return new OrderId(UUID.randomUUID().toString());
        }

        public String value() {
            return value;
        }
    }
}

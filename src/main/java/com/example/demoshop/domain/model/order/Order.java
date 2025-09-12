package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.PaymentSuccessfulEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.coupon.Coupon;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.Address;

import java.util.*;

public class Order {

    private final OrderId id;
    private final String userId;
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.CREATED;
    private final Address shippingAddress;
    private Money total;
    private Coupon appliedCoupon;

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
        recalcTotal();
    }

    private void recalcTotal() {
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
        status = OrderStatus.PLACED;
    }

    public void markPaid(PaymentSuccessfulEvent event) {
        if (this.status != OrderStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("Order cannot be paid in status: " + status);
        }
        this.status = OrderStatus.PAID;
    }

    public void markShipped() {
        if (this.status != OrderStatus.PAID) {
            throw new IllegalStateException("Only paid orders can be shipped.");
        }
        this.status = OrderStatus.SHIPPED;
    }

    // --- Getters ---

    public Address getShippingAddress() { return shippingAddress; }

    public OrderId id() { return id; }

    public List<OrderItem> items() { return Collections.unmodifiableList(items); }

    public OrderStatus status() { return status; }

    public Money total() { return total; }

    public Optional<Coupon> appliedCoupon() { return Optional.ofNullable(appliedCoupon); }

    // --- Types ---

    public enum OrderStatus { CREATED, PLACED, PENDING_PAYMENT, PAID, SHIPPED }

    public static class OrderId {
        private final String value;
        public OrderId(String value) { this.value = value; }
        public static OrderId newId() { return new OrderId(UUID.randomUUID().toString()); }
        public String value() { return value; }
    }
}

package com.example.demoshop.domain;

import com.example.demoshop.domain.model.Money;
import com.example.demoshop.domain.model.Order;
import com.example.demoshop.domain.model.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order(new Order.OrderId("test-id"));
    }

    @Test
    void newOrder_ShouldHaveCreatedStatusAndEmptyItems() {
        assertEquals(Order.OrderStatus.CREATED, order.status());
        assertTrue(order.items().isEmpty());
    }

    @Test
    void addItem_ShouldAddItemToOrder() {
        OrderItem item = new OrderItem("p1", 2, Money.of(10.0));
        order.addItem(item);

        assertEquals(1, order.items().size());
        assertEquals(item, order.items().get(0));
    }

    @Test
    void total_ShouldReturnSumOfAllItems() {
        order.addItem(new OrderItem("p1", 2, Money.of(10.0))); // 20
        order.addItem(new OrderItem("p2", 3, Money.of(5.0)));  // 15

        assertEquals(Money.of(35.0), order.total());
    }

    @Test
    void placeOrder_ShouldSetStatusToPlaced_WhenItemsExist() {
        order.addItem(new OrderItem("p1", 1, Money.of(10.0)));
        order.place();

        assertEquals(Order.OrderStatus.PLACED, order.status());
    }

    @Test
    void placeOrder_ShouldThrowException_WhenNoItems() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, order::place);
        assertEquals("Cannot place order without items", exception.getMessage());
    }

    @Test
    void addItem_ShouldThrowException_WhenQuantityIsZeroOrNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new OrderItem("p1", 0, Money.of(10.0)));
        assertEquals("Quantity must be > 0", exception.getMessage());
    }
}

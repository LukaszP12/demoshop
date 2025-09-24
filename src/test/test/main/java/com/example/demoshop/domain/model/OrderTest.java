package main.java.com.example.demoshop.domain.model;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order("user-123", List.of(), BigDecimal.ZERO, "USD", null);
    }

    @Test
    void newOrder_ShouldHavePendingStatusAndEmptyItems() {
        assertEquals(Order.OrderStatus.PENDING, order.getStatus());
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    void markPaid_ShouldChangeStatusToPaid_WhenPending() {
        order.markPaid();
        assertEquals(Order.OrderStatus.PAID, order.getStatus());
    }

    @Test
    void markShipped_ShouldChangeStatusToShipped_WhenPaid() {
        order.markPaid();
        order.markShipped();
        assertEquals(Order.OrderStatus.SHIPPED, order.getStatus());
    }

    @Test
    void markDelivered_ShouldChangeStatusToDelivered_WhenShipped() {
        order.markPaid();
        order.markShipped();
        order.markDelivered();
        assertEquals(Order.OrderStatus.DELIVERED, order.getStatus());
    }

    @Test
    void markReturned_ShouldChangeStatusToReturned_WhenDelivered() {
        order.markPaid();
        order.markShipped();
        order.markDelivered();
        order.markReturned();
        assertEquals(Order.OrderStatus.RETURNED, order.getStatus());
    }

    @Test
    void cancel_ShouldChangeStatusToCancelled_WhenPending() {
        order.cancel();
        assertEquals(Order.OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void cancel_ShouldThrowException_WhenShippedOrDelivered() {
        order.markPaid();
        order.markShipped();
        IllegalStateException ex = assertThrows(IllegalStateException.class, order::cancel);
        assertEquals("Cannot cancel a shipped order", ex.getMessage());
    }
}

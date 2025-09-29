package main.java.com.example.demoshop.java.com.example.demoshop.domain.event;

import java.time.Instant;

public record OrderCancelledEvent(String orderId, Instant time) {
}

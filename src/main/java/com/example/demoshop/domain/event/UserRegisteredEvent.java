package com.example.demoshop.domain.event;

import com.example.demoshop.domain.model.user.User;

import java.time.Instant;

public class UserRegisteredEvent {

    private final User.UserId userId;
    private final String email;
    private final Instant occurredAt;

    public UserRegisteredEvent(User.UserId userId, String email) {
        this.userId = userId;
        this.email = email;
        this.occurredAt = Instant.now();
    }

    public User.UserId userId() { return userId; }
    public String email() { return email; }
    public Instant occurredAt() { return occurredAt; }
}

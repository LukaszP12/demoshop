package example.demoshop.domain.event;

import java.time.Instant;

public class UserRegisteredEvent {

    private final Long userId;
    private final String email;
    private final Instant occurredAt;

    public UserRegisteredEvent(Long userId, String email) {
        this.userId = userId;
        this.email = email;
        this.occurredAt = Instant.now();
    }

    public Long userId() { return userId; }
    public String email() { return email; }
    public Instant occurredAt() { return occurredAt; }
}

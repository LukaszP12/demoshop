package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping;

import java.util.Objects;

public class TrackingNumber {
    private final String value;

    public TrackingNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Tracking number cannot be blank");
        }
        this.value = value;
    }

    public String value() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrackingNumber that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

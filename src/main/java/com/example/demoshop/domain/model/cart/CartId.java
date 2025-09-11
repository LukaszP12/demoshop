package com.example.demoshop.domain.model.cart;

import java.util.UUID;

public class CartId {
    private final String value;

    private CartId(String value) { this.value = value; }

    public static CartId newId() { return new CartId(UUID.randomUUID().toString()); }

    public String value() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartId)) return false;
        CartId that = (CartId) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() { return value.hashCode(); }
}

package com.example.demoshop.domain.model;

import java.util.Objects;
import java.util.UUID;

public class User {

    private final UserId id;
    private String name;
    private String email;

    public User(String name, String email) {
        this.id = UserId.newId();
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
    }

    public UserId id() { return id; }
    public String name() { return name; }
    public String email() { return email; }

    public void changeName(String newName) {
        if (newName == null || newName.isBlank()) throw new IllegalArgumentException("Name cannot be blank");
        this.name = newName;
    }

    public void changeEmail(String newEmail) {
        if (newEmail == null || newEmail.isBlank()) throw new IllegalArgumentException("Email cannot be blank");
        this.email = newEmail;
    }

    // Value object for User ID
    public static class UserId {
        private final String value;

        public UserId(String value) { this.value = value; }

        public static UserId newId() { return new UserId(UUID.randomUUID().toString()); }

        public String value() { return value; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserId)) return false;
            UserId userId = (UserId) o;
            return value.equals(userId.value);
        }

        @Override
        public int hashCode() { return value.hashCode(); }
    }
}

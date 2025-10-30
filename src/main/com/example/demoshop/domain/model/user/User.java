package example.demoshop.domain.model.user;

import example.demoshop.domain.model.order.Order;
import example.demoshop.domain.model.shipping.Address;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String username;
    private Role role;
    private Address address;
    private int loyaltyPoints = 0;

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, Role role, Address address) {
        this.role = role;
        this.address = address;
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
    }

    public User(String username, String email, String encode) {
    }

    public long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public Role role() {
        return role;
    }

    public Address address() {
        return address;
    }

    public void changeName(String newName) {
        if (newName == null || newName.isBlank()) throw new IllegalArgumentException("Name cannot be blank");
        this.name = newName;
    }

    public void changeEmail(String newEmail) {
        if (newEmail == null || newEmail.isBlank()) throw new IllegalArgumentException("Email cannot be blank");
        this.email = newEmail;
    }

    public void changeRole(Role newRole) {
        this.role = Objects.requireNonNull(newRole);
    }

    public void changeAddress(Address newAddress) {
        this.address = newAddress; // can be null if user removes address
    }

    public void earnPointsFromOrder(Order order, LoyaltyPolicy policy) {
        int earned = policy.calculatePoints(order.getTotal());
        loyaltyPoints += earned;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public String getUsername() {
        return username;
    }

    public void setRole(String roleName) {
        if (roleName == null || roleName.isBlank()) {
            this.role = Role.CUSTOMER;
            return;
        }

        try {
            this.role = Role.valueOf(roleName.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            this.role = Role.CUSTOMER;
        }
    }

    // Value object for User ID
    public static class UserId {
        private final String value;

        public UserId(String value) {
            this.value = value;
        }

        public static UserId newId() {
            return new UserId(UUID.randomUUID().toString());
        }

        public String value() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserId)) return false;
            UserId userId = (UserId) o;
            return value.equals(userId.value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }
    }
}

package main.java.com.example.demoshop.domain.model;

import com.example.demoshop.domain.event.UserRegisteredEvent;
import com.example.demoshop.domain.model.Address;
import com.example.demoshop.domain.model.Role;
import com.example.demoshop.domain.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    @Test
    void shouldAssignAndChangeRole() {
        Address address = new Address("Main St", "Berlin", "Germany", "10115");
        User user = new User("Alice", "alice@example.com", Role.CUSTOMER, address);

        assertEquals(Role.CUSTOMER,user.role());

        user.changeRole(Role.ADMIN);
        assertEquals(Role.ADMIN,user.role());
    }

    @Test
    void shouldCompareAddressesByValue() {
        Address address1 = new Address("Main St", "Berlin", "Germany", "10115");
        Address address2 = new Address("Main St", "Berlin", "Germany", "10115");

        assertEquals(address1, address2); // same values = equal
        assertEquals(address1.hashCode(), address2.hashCode());
    }

    @Test
    void shouldCreateUserRegisteredEvent() {
        Address address = new Address("Main St", "Berlin", "Germany", "10115");
        User user = new User("Bob", "bob@example.com", Role.CUSTOMER, address);

        UserRegisteredEvent event = new UserRegisteredEvent(user.id(), user.email());

        assertEquals(user.id(), event.userId());
        assertEquals("bob@example.com", event.email());
        assertNotNull(event.occurredAt());
    }
}

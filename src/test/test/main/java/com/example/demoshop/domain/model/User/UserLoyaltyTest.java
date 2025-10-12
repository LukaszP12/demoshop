package main.java.com.example.demoshop.domain.model.User;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.Address;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.Role;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserLoyaltyTest {

    private static final Currency PLN = Currency.getInstance("PLN");

    @Test
    void shouldEarnPointsBasedOnOrderTotal() {
        Address address = new Address("Main St 1", "Warsaw", "00-001", "Poland");
        User user = new User(
                "John Doe",                       // name
                "john@example.com",                // email
                Role.CUSTOMER,                     // role
                address                            // address
        );

        Order order = new Order(Money.of(BigDecimal.valueOf(250), String.valueOf(PLN)));

        user.earnPointsFromOrder(order);

        assertEquals(25, user.getLoyaltyPoints());
    }

}

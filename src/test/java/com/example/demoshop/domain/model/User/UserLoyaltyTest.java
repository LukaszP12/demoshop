package java.com.example.demoshop.domain.model.User;

import com.example.demoshop.domain.model.order.Order;
import com.example.demoshop.domain.model.user.Address;
import com.example.demoshop.domain.model.user.Role;
import com.example.demoshop.domain.model.user.User;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.coupon.Coupon;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.OrderItem;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.LoyaltyPolicy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

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

        List<OrderItem> items = Collections.emptyList();

        Coupon coupon = null;

        Order order = new Order(
                user.email(),         // userId (you might use user.getId() if you have one)
                items,                   // order items
                BigDecimal.valueOf(250), // total amount
                PLN.getCurrencyCode(),   // currency as String
                coupon                   // optional coupon
        );

        LoyaltyPolicy policy = new LoyaltyPolicy(BigDecimal.TEN);

        user.earnPointsFromOrder(order,policy);

        assertEquals(25, user.getLoyaltyPoints());
    }
}

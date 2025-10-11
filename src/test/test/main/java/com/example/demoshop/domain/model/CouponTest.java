package main.java.com.example.demoshop.domain.model;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.coupon.Coupon;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.coupon.exceptions.CouponNotApplicableException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CouponTest {

    @Test
    void shouldRejectCouponWhenOrderBelowMinimumAmount(){
        Coupon coupon = new Coupon("SAVE10",
                new BigDecimal(10),
                true,
                LocalDate.now().plusDays(1),
                100);
        BigDecimal orderTotal = BigDecimal.valueOf(80);

        assertThrows(CouponNotApplicableException.class,
                () -> coupon.apply(orderTotal));
    }

}

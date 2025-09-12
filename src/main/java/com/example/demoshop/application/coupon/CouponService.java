package main.java.com.example.demoshop.java.com.example.demoshop.application.coupon;


import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.coupon.Coupon;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public BigDecimal applyCoupon(String code, BigDecimal orderTotal) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Invalid coupon code"));

        BigDecimal discountedTotal = coupon.apply(orderTotal);
        coupon.incrementUsage();
        couponRepository.save(coupon);

        return discountedTotal;
    }
}

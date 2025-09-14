package main.java.com.example.demoshop.java.com.example.demoshop.application.coupon;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.event.CouponUsedEvent;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.coupon.Coupon;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.CouponRepository;
import org.springframework.stereotype.Service;


@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final RabbitTemplate rabbitTemplate;

    public CouponService(CouponRepository couponRepository, RabbitTemplate rabbitTemplate) {
        this.couponRepository = couponRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void applyCouponToOrder(Order order, Coupon coupon) {
        // 1. Apply coupon in the domain
        order.applyCoupon(coupon);

        // 2. Update coupon usage
        coupon.incrementUsage();
        couponRepository.save(coupon);

        // 3. Publish event to RabbitMQ
        rabbitTemplate.convertAndSend(
                "events-exchange",
                "coupon.used",
                new CouponUsedEvent(coupon.getCode(), order.id().value())
        );
    }
}

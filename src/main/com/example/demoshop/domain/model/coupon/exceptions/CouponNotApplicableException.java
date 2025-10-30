package example.demoshop.domain.model.coupon.exceptions;

public class CouponNotApplicableException extends RuntimeException{
    public CouponNotApplicableException(String message) {
        super(message);
    }
}

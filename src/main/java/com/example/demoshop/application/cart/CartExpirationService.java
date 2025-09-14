package main.java.com.example.demoshop.java.com.example.demoshop.application.cart;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.Cart;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.CartId;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.CartRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartExpirationService {

    private final CartRepository cartRepository;
    private final long expirationSeconds = 24 * 60 * 60; // 24 hours

    public CartExpirationService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // Runs every hour
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void removeExpiredCarts() {
        List<Cart> carts = cartRepository.findAll();
        for (Cart cart : carts) {
            if (cart.isExpired(expirationSeconds)) {
                cartRepository.delete(new CartId(cart.userId()));
            }
        }
    }
}

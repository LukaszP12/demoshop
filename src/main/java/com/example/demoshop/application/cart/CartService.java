package main.java.com.example.demoshop.java.com.example.demoshop.application.cart;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.Cart;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.CartId;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.CartItem;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Product;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final long expirationSeconds = 24 * 60 * 60; // 24 hours

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart getCart(String userId){
        Cart cart = cartRepository.findById(new CartId(userId)).orElseThrow(
                () -> new RuntimeException("Cart with id: " + userId + " not found.")
        );
        return cart;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll().stream()
                .filter(cart -> !cart.isExpired(expirationSeconds))
                .toList();
    }

    public void addItem(String userId, CartItem item) {
        Cart cart = cartRepository.findById(new CartId(userId)).orElse(new Cart(userId));
        cart.addProduct(new Product(item.productName(),item.unitPrice(),item.quantity()),
                item.quantity());
        cartRepository.save(cart);
    }
}

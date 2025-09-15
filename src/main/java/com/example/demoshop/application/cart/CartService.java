package main.java.com.example.demoshop.java.com.example.demoshop.application.cart;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.Cart;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.CartId;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.CartItem;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Product;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.ProductId;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.CartRepository;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final long expirationSeconds = 24 * 60 * 60; // 24 hours

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart getCart(String userId){
        return cartRepository.findById(new CartId(userId))
                .filter(cart -> !cart.isExpired(expirationSeconds))
                .orElseThrow(() -> new RuntimeException("Cart with id: " + userId + " not found."));
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

    public void updateItemQuantity(String userId, String productId, int quantity) {
        Cart cart = cartRepository.findById(new CartId(userId))
                .filter(c -> !c.isExpired(expirationSeconds))
                .orElseThrow(() -> new RuntimeException("Cart not found or expired for user: " + userId));

        Product product = productRepository.findById(new ProductId(productId))
                .orElseThrow(() -> new RuntimeException("Product with id: " + productId + " not found "));

        cart.items().stream()
                .filter(item -> item.productId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                    item -> {
                            if (quantity > 0){
                                item.setQuantity(quantity);
                            } else {
                                cart.items().remove(item);
                            }
                    },
                        () -> {
                            if (quantity > 0){
                                cart.addItem(new CartItem(product,quantity));
                            }
                        }
                );

        cartRepository.save(cart);
    }

    public void removeItem(String cartId, String productId){
        Cart cart = cartRepository.findById(new CartId(cartId))
                .filter(c -> !c.isExpired(expirationSeconds))
                .orElseThrow(() -> new RuntimeException("Cart not found or expired: " + cartId));

        cart.removeItem(new ProductId(productId));
        cartRepository.save(cart);
    }
}

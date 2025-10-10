package main.java.com.example.demoshop.java.com.example.demoshop.presentation.cart;

import main.java.com.example.demoshop.java.com.example.demoshop.application.cart.CartService;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.Cart;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.CartItem;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import main.java.com.example.demoshop.java.com.example.demoshop.presentation.cart.dto.CartSummary;
import main.java.com.example.demoshop.java.com.example.demoshop.presentation.cart.dto.CheckoutRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable String userId) {
        return cartService.getCart(userId); // returns active cart or empty cart
    }

    @PostMapping("/{userId}/items")
    public void addItemToCart(@PathVariable String userId, @RequestBody CartItem item) {
        cartService.addItem(userId, item);
    }

    @PutMapping("/{userId}/items/{productId}")
    public void update(@PathVariable String userId,
                       @PathVariable String productId,
                       @RequestParam int quantity) {
        cartService.updateItemQuantity(userId, productId, quantity);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public void removeItem(@PathVariable String cartId, @PathVariable String productId) {
        cartService.removeItem(cartId, productId);
    }

    @DeleteMapping("/{cartId}")
    public void clearCart(@PathVariable String cartId) {
        cartService.clearCart(cartId);
    }

    @GetMapping("/{cartId}/summary")
    public CartSummary getCartSummary(@PathVariable String cartId) {
        return cartService.getCartSummary(cartId);
    }

    @PostMapping("/{cartId}/checkout")
    public Order checkout(@PathVariable String cartId,@RequestBody CheckoutRequest request) {
        return cartService.checkout(cartId, request.userId(), request.couponCode());
    }
}

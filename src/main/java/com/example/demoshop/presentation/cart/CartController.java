package main.java.com.example.demoshop.java.com.example.demoshop.presentation.cart;

import main.java.com.example.demoshop.java.com.example.demoshop.application.cart.CartService;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.Cart;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.CartItem;
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
                       @RequestParam int quantity){
        cartService.updateItemQuantity(userId,productId, quantity);
    }
}

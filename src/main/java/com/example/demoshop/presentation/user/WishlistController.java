package main.java.com.example.demoshop.java.com.example.demoshop.presentation.user;

import main.java.com.example.demoshop.java.com.example.demoshop.application.user.WishlistService;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.Wishlist;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/{userId}")
    public Wishlist getWishlist(@PathVariable String userId) {
        return wishlistService.getWishlist(userId);
    }

    @PostMapping("/{userId}/items/{productId}")
    public Wishlist addProduct(@PathVariable String userId, @PathVariable String productId) {
        return wishlistService.addProduct(userId, productId);
    }

    @PostMapping("/{userId}/move-to-cart/{productId}")
    public void moveToCart(@PathVariable String userId, @PathVariable String productId) {
        wishlistService.moveToCart(userId, productId);
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public Wishlist removeProduct(@PathVariable String userId, @PathVariable String productId) {
        return wishlistService.removeProduct(userId, productId);
    }

    @DeleteMapping("/{userId}")
    public void clearWishlist(@PathVariable String userId) {
        wishlistService.clearWishlist(userId);
    }
}

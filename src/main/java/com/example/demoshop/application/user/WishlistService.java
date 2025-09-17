package main.java.com.example.demoshop.java.com.example.demoshop.application.user;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.Wishlist;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist getWishlist(String userId) {
        return wishlistRepository.findById(userId)
                .orElseGet(() -> {
                    Wishlist newWishlist = new Wishlist();
                    newWishlist.setUserId(userId);
                    newWishlist.setProductIds(new ArrayList<>());
                    return wishlistRepository.save(newWishlist);
                });
    }

    public Wishlist addProduct(String userId, String productId) {
        Wishlist wishlist = getWishlist(userId);
        if (!wishlist.getProductIds().contains(productId)) {
            wishlist.getProductIds().add(productId);
        }
        return wishlistRepository.save(wishlist);
    }

    public Wishlist removeProduct(String userId, String productId) {
        Wishlist wishlist = getWishlist(userId);
        wishlist.getProductIds().remove(productId);
        return wishlistRepository.save(wishlist);
    }

    public void clearWishlist(String userId) {
        wishlistRepository.findByUserId(userId)
                .ifPresent(wishlistRepository::delete);
    }
}

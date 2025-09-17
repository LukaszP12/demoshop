package main.java.com.example.demoshop.java.com.example.demoshop.application.user;

import main.java.com.example.demoshop.java.com.example.demoshop.application.cart.CartService;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart.CartItem;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Product;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.ProductId;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.Wishlist;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.ProductRepository;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository, CartService cartService) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
    }

    public void moveToCart(String userId,String productId){
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        // check if product is in the wishlist
        if (!wishlist.getProductIds().contains(productId)){
            throw new RuntimeException("Product not in wishlist");
        }

        Product product = productRepository.findById(new ProductId(productId))
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // add to cart
        try {
            cartService.addItem(userId,new CartItem(product,1));
        } catch (Exception e) {
            throw new RuntimeException("Cannot move product to cart: " + e.getMessage());
        }

        // remove from wishlist
        wishlist.getProductIds().remove(productId);
        wishlistRepository.save(wishlist);
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

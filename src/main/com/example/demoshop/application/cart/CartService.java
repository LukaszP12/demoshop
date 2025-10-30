package example.demoshop.application.cart;

import example.demoshop.application.order.OrderWorkflowService;
import example.demoshop.domain.model.cart.Cart;
import example.demoshop.domain.model.cart.CartItem;
import example.demoshop.domain.model.catalogue.Product;
import example.demoshop.domain.model.catalogue.ProductId;
import example.demoshop.domain.model.common.Money;
import example.demoshop.domain.model.order.Order;
import example.demoshop.domain.repository.CartRepository;
import example.demoshop.domain.repository.ProductRepository;
import example.demoshop.domain.repository.UserRepository;
import example.demoshop.presentation.cart.dto.CartSummary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final OrderWorkflowService orderService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final long expirationSeconds = 24 * 60 * 60; // 24 hours

    public CartService(CartRepository cartRepository,
                       OrderWorkflowService orderService,
                       @Qualifier("postgresUserRepository") UserRepository userRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Cart getCart(String userId) {
        return cartRepository.findByUserId(userId)
                .filter(cart -> !cart.isExpired(expirationSeconds))
                .orElseThrow(() -> new RuntimeException("Cart with id: " + userId + " not found."));
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll().stream()
                .filter(cart -> !cart.isExpired(expirationSeconds))
                .toList();
    }

    public void addItem(String userId, CartItem item) {
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart(userId));
        cart.addItem(item);
        cartRepository.save(cart);
    }

    public void updateItemQuantity(String userId, String productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .filter(c -> !c.isExpired(expirationSeconds))
                .orElseThrow(() -> new RuntimeException("Cart not found or expired for user: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product with id: " + productId + " not found "));

        cart.items().stream()
                .filter(item -> item.productId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            if (quantity > 0) {
                                item.setQuantity(quantity);
                            } else {
                                cart.items().remove(item);
                            }
                        },
                        () -> {
                            if (quantity > 0) {
                                cart.addItem(new CartItem(product, quantity, product.getPrice()));
                            }
                        }
                );

        cartRepository.save(cart);
    }

    public void removeItem(String cartId, String productId) {
        Cart cart = cartRepository.findByUserId(cartId)
                .filter(c -> !c.isExpired(expirationSeconds))
                .orElseThrow(() -> new RuntimeException("Cart not found or expired: " + cartId));

        cart.removeItem(new ProductId(productId));
        cartRepository.save(cart);
    }

    public void clearCart(String cartId) {
        Cart cart = cartRepository.findByUserId(cartId)
                .filter(c -> !c.isExpired(expirationSeconds))
                .orElseThrow(() -> new RuntimeException("Cart not found or expired: " + cartId));

        cart.clearItems();
        cartRepository.save(cart);
    }

    public CartSummary getCartSummary(String cartId) {
        Cart cart = cartRepository.findByUserId(cartId)
                .filter(c -> !c.isExpired(expirationSeconds))
                .orElseThrow(() -> new RuntimeException("Cart not found or expired: " + cartId));

        int totalItems = cart.items().stream()
                .mapToInt(CartItem::quantity)
                .sum();

        Money totalPrice = cart.items().stream()
                .map(item -> item.unitPrice().multiply(new BigDecimal(item.quantity())))
                .reduce(Money.zero(""), Money::add);

        return new CartSummary(totalItems, totalPrice.getAmount().doubleValue());
    }

    public Order checkout(String cartId, String userId, String couponCode) {
        Cart cart = cartRepository.findByUserId(cartId)
                .filter(c -> !c.isExpired(expirationSeconds))
                .orElseThrow(() -> new RuntimeException("Cart not found or expired: " + cartId));

        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Order order = orderService.createOrderFromCart(userId, couponCode);

        cart.clearItems();
        cartRepository.save(cart);

        return order;
    }
}

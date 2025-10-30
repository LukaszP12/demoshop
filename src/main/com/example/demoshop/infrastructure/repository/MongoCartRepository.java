package example.demoshop.infrastructure.repository;

import example.demoshop.domain.model.cart.Cart;
import example.demoshop.domain.model.cart.CartId;
import example.demoshop.domain.repository.CartRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MongoCartRepository implements CartRepository {

    private final SpringDataCartRepository delegate;

    public MongoCartRepository(SpringDataCartRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public Optional<Cart> findByUserId(String userId) {
        return delegate.findByUserId(userId);
    }

    @Override
    public void save(Cart cart) {
        delegate.save(cart);
    }
    @Override
    public List<Cart> findAll() {
        return List.of();
    }

    @Override
    public void delete(CartId id) {
        delegate.deleteById(id.value());
    }

    @Override
    public void clearCart(String userId) {
        delegate.findByUserId(userId).ifPresent(cart -> {
            cart.clearItems(); // ✅ assumes your Cart aggregate has this method
            delegate.save(cart);
        });
    }
}

package example.demoshop.infrastructure.repository;

import example.demoshop.domain.model.order.Order;
import example.demoshop.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MongoOrderRepository implements OrderRepository {

    private final SpringDataOrderRepository repository;

    public MongoOrderRepository(SpringDataOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        repository.save(order);
        return order;
    }

    @Override
    public void saveAll(List<Order> orders) {
        repository.saveAll(orders);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<Order> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Order> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }
}

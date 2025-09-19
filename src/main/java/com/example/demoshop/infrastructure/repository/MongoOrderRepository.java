package main.java.com.example.demoshop.java.com.example.demoshop.infrastructure.repository;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.order.Order;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.OrderRepository;
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
    public void save(Order order) {
        repository.save(order);
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
    public List<Order> findByUserId(String customerId) {
        return repository.findByCustomerId(customerId);
    }
}

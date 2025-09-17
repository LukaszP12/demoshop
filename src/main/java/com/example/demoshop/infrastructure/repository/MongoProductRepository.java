package main.java.com.example.demoshop.java.com.example.demoshop.infrastructure.repository;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Product;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MongoProductRepository implements ProductRepository {

    private final SpringDataProductRepository repository;

    public MongoProductRepository(SpringDataProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Product product) {
        repository.save(product);
    }

    @Override
    public void saveAll(List<Product> products) {
        repository.saveAll(products);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<Product> findById(String id) {
        return repository.findById(id);
    }
}

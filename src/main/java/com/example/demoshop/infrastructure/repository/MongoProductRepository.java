package com.example.demoshop.infrastructure.repository;

import com.example.demoshop.domain.model.catalogue.Product;
import com.example.demoshop.domain.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

    @Override
    public Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable) {
        return repository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(name, description, pageable);
    }

    @Override
    public Page<Product> findByCategories_Name(String category, Pageable pageable) {
        return repository.findByCategories_Name(category, pageable);
    }

    @Override
    public Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return repository.findByPriceBetween(minPrice, maxPrice, pageable);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Product> findByKeywordsIn(List<String> keywords, Pageable pageable) {
        return repository.findByKeywordsIn(keywords, pageable);
    }
}

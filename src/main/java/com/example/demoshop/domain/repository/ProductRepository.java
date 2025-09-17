package main.java.com.example.demoshop.java.com.example.demoshop.domain.repository;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void save(Product product);
    void saveAll(List<Product> products);
    long count();
    Optional<Product> findById(String id);
}

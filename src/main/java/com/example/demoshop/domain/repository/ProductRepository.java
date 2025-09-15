package main.java.com.example.demoshop.java.com.example.demoshop.domain.repository;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Product;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.ProductId;

import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(ProductId id);
}

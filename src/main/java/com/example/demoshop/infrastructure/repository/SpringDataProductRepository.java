package main.java.com.example.demoshop.java.com.example.demoshop.infrastructure.repository;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataProductRepository extends MongoRepository<Product, String> { }
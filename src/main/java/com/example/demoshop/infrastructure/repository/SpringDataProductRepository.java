package main.java.com.example.demoshop.java.com.example.demoshop.infrastructure.repository;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;

public interface SpringDataProductRepository extends MongoRepository<Product, String> {

    // filter by category
    Page<Product> findByCategories_Name(String categoryName, Pageable pageable);

    // filter by price range
    Page<Product> findByPriceBetween(BigDecimal min, BigDecimal max,Pageable pageable);

    // search by name or description
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String nameKeyword,
            String descriptionKeyword,
            Pageable pageable
    );
}

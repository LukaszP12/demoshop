package main.java.com.example.demoshop.java.com.example.demoshop.application.product;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Product;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Page<Product> listProducts(String category, BigDecimal minPrice, BigDecimal maxPrice, String search, Pageable pageable);

    Page<Product> findByKeywordsIn(List<String> keywords, Pageable pageable);
}

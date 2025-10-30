package example.demoshop.application.product;

import example.demoshop.domain.model.catalogue.Product;
import example.demoshop.domain.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> listProducts(String category, BigDecimal minPrice, BigDecimal maxPrice, String search, Pageable pageable) {
        if (search != null && !search.isBlank()) {
            return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, pageable);
        } else if (category != null && !category.isBlank()) {
            return productRepository.findByCategories_Name(category, pageable);
        } else if (minPrice != null && maxPrice != null) {
            return productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    @Override
    public Page<Product> findByKeywordsIn(List<String> keywords, Pageable pageable) {
        return productRepository.findByKeywordsIn(keywords, pageable);
    }
}

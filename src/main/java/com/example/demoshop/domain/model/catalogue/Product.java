package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "products")
public class Product {

    @Id
    private final ProductId id;
    private final String name;
    private final Money price;
    private int stock;
    private final Set<Category> categories = new HashSet<>();
    private final Set<ProductVariant> variants = new HashSet<>();
    private final Set<ProductReview> reviews = new HashSet<>();

    public Product(String name, Money price, int stock) {
        this.id = ProductId.newId();
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void addVariant(ProductVariant variant) {
        variants.add(variant);
    }

    public Set<ProductVariant> variants() {
        return Set.copyOf(variants);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public Set<Category> categories() {
        return Set.copyOf(categories);
    }

    public void addReview(ProductReview review) {
        reviews.add(review);
    }

    public Set<ProductReview> reviews() {
        return Set.copyOf(reviews);
    }

    public double averageRating() {
        return reviews.stream().mapToInt(ProductReview::rating).average().orElse(0);
    }

    public void increaseStock(int amount) {
        this.stock += amount;
    }
}

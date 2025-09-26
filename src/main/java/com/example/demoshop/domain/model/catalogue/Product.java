package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private String type;
    private String brand;
    private double price;
    private int volume_ml;
    private int stock;
    private String description;
    private List<ProductCategory> categories;
    private List<String> keywords;
    private double rating;
    private boolean available;

    public Product() {
    }

    public Product(String name, String type, String brand, double price, int volume_ml, int stock, String description, List<ProductCategory> categories, List<String> keywords, double rating, boolean available) {
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.volume_ml = volume_ml;
        this.stock = stock;
        this.description = description;
        this.categories = categories;
        this.keywords = keywords;
        this.rating = rating;
        this.available = available;
    }

    public void decreaseStock(int quantity) {
        if (quantity > stock) {
            throw new IllegalStateException("Not enough stock for product " + name);
        }
        stock -= quantity;
    }

    public void increaseStock(int quantity) {
        stock += quantity;
    }

    public void addCategory(ProductCategory category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }

    public void removeCategory(ProductCategory category) {
        categories.remove(category);
    }

    public void addKeyword(String keyword){
        if (!keywords.contains(keyword)){
            keywords.add(keyword);
        }
    }

    public void removeKeyword(String keyword){
        keywords.remove(keyword);
    }

    // Getters and setters
    public ProductId getId() {
        return new ProductId(id);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public int getVolume_ml() {
        return volume_ml;
    }

    public int getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setVolume_ml(int volume_ml) {
        this.volume_ml = volume_ml;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getKeywords() { return keywords; }

    public void setKeywords(List<String> keywords) {this.keywords = keywords;}

    public List<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ProductCategory> categories) {
        this.categories = categories;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

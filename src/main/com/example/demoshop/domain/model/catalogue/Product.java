package example.demoshop.domain.model.catalogue;

import example.demoshop.domain.model.common.Money;
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
    private Money price;
    private int volume_ml;
    private int stockQuantity;
    private String description;
    private List<ProductCategory> categories;
    private List<String> keywords;
    private double rating;
    private boolean available;

    public Product() {
    }

    public Product(String id, String name, String description, Money price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Product(String name, String type,
                   String brand,
                   Money price,
                   int volume_ml,
                   int stockQuantity, String description,
                   List<ProductCategory> categories, List<String> keywords, double rating, boolean available) {
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.volume_ml = volume_ml;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.categories = categories;
        this.keywords = keywords;
        this.rating = rating;
        this.available = available;
    }

    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Cannot increase by zero or negative number " + name);
        }
        if (quantity > stockQuantity) {
            throw new IllegalStateException("Not enough stock for product " + name);
        }
        stockQuantity -= quantity;
    }

    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Cannot increase by zero or negative number " + name);
        }
        stockQuantity += quantity;
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

    public Money getPrice() {
        return price;
    }

    public int getVolume_ml() {
        return volume_ml;
    }

    public int getStockQuantity() {
        return stockQuantity;
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

    public void setPrice(Money price) {
        this.price = price;
    }

    public void setVolume_ml(int volume_ml) {
        this.volume_ml = volume_ml;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
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

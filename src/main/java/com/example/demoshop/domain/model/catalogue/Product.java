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

    public Product() {}

    public Product(String name, String type, String brand, double price, int volume_ml, int stock, String description, List<ProductCategory> categories) {
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.volume_ml = volume_ml;
        this.stock = stock;
        this.description = description;
        this.categories = categories;
    }

    public void addCategory(ProductCategory category){
        if (!categories.contains(category)){
            categories.add(category);
        }
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public int getVolume_ml() { return volume_ml; }
    public int getStock() { return stock; }
    public String getDescription() { return description; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setPrice(double price) { this.price = price; }
    public void setVolume_ml(int volume_ml) { this.volume_ml = volume_ml; }
    public void setStock(int stock) { this.stock = stock; }
    public void setDescription(String description) { this.description = description; }
}

package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue;

public class ProductVariant {
    private String sku;   // unique stock keeping unit
    private String size;
    private String color;
    private int stockQuantity;

    public ProductVariant(String sku, String size, String color, int stockQuantity) {
        this.sku = sku;
        this.size = size;
        this.color = color;
        this.stockQuantity = stockQuantity;
    }

    public String getSku() { return sku; }
    public String getSize() { return size; }
    public String getColor() { return color; }
    public int getStockQuantity() { return stockQuantity; }
}

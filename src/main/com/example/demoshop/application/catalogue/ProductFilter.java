package example.demoshop.application.catalogue;

import java.math.BigDecimal;
import java.util.List;

public class ProductFilter {

    private final BigDecimal minPrice;
    private final BigDecimal maxPrice;
    private final List<String> categories;
    private final List<String> brands;
    private final Double minRating;
    private final Boolean available;
    private final String searchTerm;

    private ProductFilter(Builder builder) {
        this.minPrice = builder.minPrice;
        this.maxPrice = builder.maxPrice;
        this.categories = builder.categories != null ? builder.categories : List.of();
        this.brands = builder.brands != null ? builder.brands : List.of();
        this.minRating = builder.minRating;
        this.available = builder.available;
        this.searchTerm = builder.searchTerm;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<String> getBrands() {
        return brands;
    }

    public Double getMinRating() {
        return minRating;
    }

    public Boolean getAvailable() {
        return available;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    // --- Builder Pattern ---
    public static class Builder {
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
        private List<String> categories;
        private List<String> brands;
        private Double minRating;
        private Boolean available;
        private String searchTerm;

        public Builder minPrice(BigDecimal minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public Builder maxPrice(BigDecimal maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public Builder categories(List<String> categories) {
            this.categories = categories;
            return this;
        }

        public Builder brands(List<String> brands) {
            this.brands = brands;
            return this;
        }

        public Builder minRating(Double minRating) {
            this.minRating = minRating;
            return this;
        }

        public Builder available(Boolean available) {
            this.available = available;
            return this;
        }

        public Builder searchTerm(String searchTerm) {
            this.searchTerm = searchTerm;
            return this;
        }

        public ProductFilter build() {
            return new ProductFilter(this);
        }
    }
}

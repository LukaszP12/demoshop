package example.demoshop.domain.model.catalogue;

import java.util.Objects;

public class ProductCategory {
    private final String name;

    public ProductCategory(String name) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCategory)) return false;
        ProductCategory that = (ProductCategory) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "name='" + name + '\'' +
                '}';
    }
}

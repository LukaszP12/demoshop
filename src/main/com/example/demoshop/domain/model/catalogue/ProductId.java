package example.demoshop.domain.model.catalogue;

import java.util.Objects;
import java.util.UUID;

public class ProductId {

    private final String id;

    public ProductId(String id) {
        this.id = id;
    }

    public static ProductId newId() {
        return new ProductId(UUID.randomUUID().toString());
    }

    public static ProductId of(String id) {
        return new ProductId(id);
    }

    public String value() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductId)) return false;
        ProductId productId = (ProductId) o;
        return id.equals(productId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

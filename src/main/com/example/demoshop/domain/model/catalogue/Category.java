package example.demoshop.domain.model.catalogue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "categories")
public class Category {

    @Id
    private String id;
    private final String name;
    private String parentCategoryId;

    public Category(String name, String parentCategoryId) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        this.name = name;
        this.parentCategoryId = parentCategoryId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getParentCategoryId() { return parentCategoryId; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Category category)) return false;
        return Objects.equals(getId(), category.getId()) && Objects.equals(getName(), category.getName()) && Objects.equals(getParentCategoryId(), category.getParentCategoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getParentCategoryId());
    }
}

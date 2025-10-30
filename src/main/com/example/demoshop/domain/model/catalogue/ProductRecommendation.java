package example.demoshop.domain.model.catalogue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "recommendations")
public class ProductRecommendation {

    @Id
    private String productId;  // the base product
    private List<String> recommendedProductIds;

    public ProductRecommendation(String productId, List<String> recommendedProductIds) {
        this.productId = productId;
        this.recommendedProductIds = recommendedProductIds;
    }

    public String getProductId() { return productId; }
    public List<String> getRecommendedProductIds() { return recommendedProductIds; }
}

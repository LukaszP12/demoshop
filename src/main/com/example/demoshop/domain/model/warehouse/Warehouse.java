package example.demoshop.domain.model.warehouse;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {

    private final String name;
    private final Map<String, Integer> stock = new HashMap<>();

    public Warehouse(String name) {
        this.name = name;
    }

    public void restock(String productId, int quantity) {
        stock.put(productId, stock.getOrDefault(productId, 0) + quantity);
    }

    public void reserve(String productId, int quantity) {
        getOrCreateItem(productId).reserve(quantity);
    }

    public int getAvailableQuantity(String productId) {
        return stock.getOrDefault(productId, 0);
    }

    private InventoryItem getOrCreateItem(String productId) {
        return stock.computeIfAbsent(productId, InventoryItem::new);
    }
}

package example.demoshop.domain.model.warehouse;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {

    private final String name;
    private final Map<String, InventoryItem> items = new HashMap<>();

    public Warehouse(String name) {
        this.name = name;
    }

    public void restock(String productId, int quantity) {
        getOrCreateItem(productId).restock(quantity);
    }

    public void reserve(String productId, int quantity) {
        getOrCreateItem(productId).reserve(quantity);
    }

    public int getAvailableQuantity(String productId) {
        return getOrCreateItem(productId).getAvailable();
    }

    public int getReservedQuantity(String productId) {
        return getOrCreateItem(productId).getReserved();
    }

    private InventoryItem getOrCreateItem(String productId) {
        return items.computeIfAbsent(productId, InventoryItem::new);
    }

    public void ship(String productId, int quantity) {
        getOrCreateItem(productId).ship(quantity);
    }
}

package example.demoshop.domain.model.warehouse;

public class InventoryItem {

    private final String productId;
    private int available;
    private int reserved;

    public InventoryItem(String productId) {
        this.productId = productId;
    }

    public void restock(int quantity) {
        available += quantity;
    }

    public void reserve(int quantity) {
        if (available < quantity) {
            throw new IllegalStateException("Not enough stock to reserve");
        }
        available -= quantity;
        reserved += quantity;
    }

    public int getAvailable() {
        return available;
    }

    public int getReserved() {
        return reserved;
    }

    // an item must be reserved first before it can be shipped out.
    public void ship(int quantity) {
        if (reserved < quantity) {
            throw new IllegalStateException("Cannot ship more than reserved");
        }
        reserved -= quantity;
    }
}

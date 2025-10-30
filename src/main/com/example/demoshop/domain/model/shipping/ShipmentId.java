package example.demoshop.domain.model.shipping;

import java.util.UUID;

public class ShipmentId {
    private final String id;

    private ShipmentId(String id) {
        this.id = id;
    }

    public static ShipmentId newId() {
        return new ShipmentId(UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }
}


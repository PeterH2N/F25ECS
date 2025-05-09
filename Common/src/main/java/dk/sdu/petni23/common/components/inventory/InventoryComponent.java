package dk.sdu.petni23.common.components.inventory;

import dk.sdu.petni23.common.components.items.ItemComponent;
import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InventoryComponent extends Component {
    public boolean visible = false;
    public final int maxAmount;
    // NEW: Inventory amounts
    public Map<IEntitySPI.Type, Integer> amounts = new HashMap<>();

    public InventoryComponent(int maxAmount, IEntitySPI.Type... types) {
        Arrays.stream(types).forEach(type -> amounts.put(type, 0));
        this.maxAmount = maxAmount;
    }
}

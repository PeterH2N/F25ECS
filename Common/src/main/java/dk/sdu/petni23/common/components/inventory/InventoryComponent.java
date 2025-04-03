package dk.sdu.petni23.common.components.inventory;

import dk.sdu.petni23.common.components.items.ItemComponent;
import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;

import java.util.HashMap;
import java.util.Map;

public class InventoryComponent extends Component {
    public boolean visible = false;

    // NEW: Inventory amounts
    public Map<IEntitySPI.Type, Integer> amounts = new HashMap<>();
}

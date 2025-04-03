package dk.sdu.petni23.common.components.items;

import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.gameengine.Component;

import dk.sdu.petni23.gameengine.entity.IEntitySPI;  // Import the IEntitySPI interface

public class ItemComponent extends Component {
    public Dispatch onPickup;
    public IEntitySPI.Type itemType;  // Use IEntitySPI.Type to refer to the enum

    // Constructor to set the item type
    public ItemComponent(IEntitySPI.Type type) {
        this.itemType = type;
    }
}
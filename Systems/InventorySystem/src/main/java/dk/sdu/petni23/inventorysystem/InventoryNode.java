package dk.sdu.petni23.inventorysystem;

import dk.sdu.petni23.common.components.inventory.InventoryComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class InventoryNode extends Node
{
    public InventoryComponent inventoryComponent;
    public InventoryNode(Entity entity)
    {
        super(entity);
    }

    @Override
    public void onRemove() {
        InventorySystem.releasePlayerInventory();
    }

    @Override
    public void onAdd() {
        InventorySystem.setPlayerInventory();
    }
}

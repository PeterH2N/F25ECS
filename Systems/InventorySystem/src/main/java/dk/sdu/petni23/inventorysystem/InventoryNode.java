package dk.sdu.petni23.inventorysystem;

import dk.sdu.petni23.common.components.inventory.InventoryComponent;
import dk.sdu.petni23.common.components.inventory.WalletComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.Optional;

public class InventoryNode extends Node
{
    public InventoryComponent inventoryComponent;
    @Optional
    public WalletComponent walletComponent;
    public InventoryNode(Entity entity)
    {
        super(entity);
    }
}

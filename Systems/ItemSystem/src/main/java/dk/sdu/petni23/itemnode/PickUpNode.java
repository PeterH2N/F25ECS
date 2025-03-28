package dk.sdu.petni23.itemnode;

import dk.sdu.petni23.common.components.inventory.InventoryComponent;
import dk.sdu.petni23.common.components.inventory.PickUpComponent;
import dk.sdu.petni23.common.components.inventory.WalletComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.Optional;

public class PickUpNode extends Node
{
    public PickUpComponent pickUpComponent;
    public PositionComponent positionComponent;
    @Optional
    public WalletComponent walletComponent;
    @Optional
    public InventoryComponent inventoryComponent;
    public PickUpNode(Entity entity)
    {
        super(entity);
    }
}

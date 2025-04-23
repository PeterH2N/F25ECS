package dk.sdu.petni23.itemnode;

import dk.sdu.petni23.common.components.items.CurrencyComponent;
import dk.sdu.petni23.common.components.items.ItemComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.OptionalComponent;

public class ItemNode extends Node
{
    public ItemComponent itemComponent;
    public PositionComponent positionComponent;
    public VelocityComponent velocityComponent;
    @OptionalComponent
    public CurrencyComponent currencyComponent;
    public ItemNode(Entity entity)
    {
        super(entity);
    }

    @Override
    public void onRemove() {

    }

    @Override
    public void onAdd() {

    }
}

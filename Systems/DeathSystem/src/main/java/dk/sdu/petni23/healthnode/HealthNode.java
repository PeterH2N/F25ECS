package dk.sdu.petni23.healthnode;

import dk.sdu.petni23.common.components.items.LootComponent;
import dk.sdu.petni23.common.components.life.HealthComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.Optional;

public class HealthNode extends Node
{
    public HealthComponent healthComponent;
    @Optional
    public PositionComponent positionComponent;
    @Optional
    public LootComponent lootComponent;
    public HealthNode(Entity entity)
    {
        super(entity);
    }
}

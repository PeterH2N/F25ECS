package dk.sdu.petni23.healthnode;

import dk.sdu.petni23.common.components.items.LootComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.OptionalComponent;

public class HealthNode extends Node
{
    public HealthComponent healthComponent;
    @OptionalComponent
    public LootComponent lootComponent;
    public HealthNode(Entity entity)
    {
        super(entity);
    }

    @Override
    public void onRemove() {

    }
}

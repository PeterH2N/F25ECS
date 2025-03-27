package dk.sdu.petni23.collisionnode;

import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.life.HealthComponent;
import dk.sdu.petni23.common.components.life.LayerComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.Optional;

public class HitBoxNode extends Node
{
    public HitBoxComponent hitBoxComponent;
    public PositionComponent positionComponent;
    public LayerComponent layerComponent;
    @Optional
    public HealthComponent healthComponent;
    public HitBoxNode(Entity entity)
    {
        super(entity);
    }
}

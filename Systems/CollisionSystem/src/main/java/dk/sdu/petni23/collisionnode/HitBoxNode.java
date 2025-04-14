package dk.sdu.petni23.collisionnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.OptionalComponent;

public class HitBoxNode extends Node
{
    public HitBoxComponent hitBoxComponent;
    public PositionComponent positionComponent;
    public LayerComponent layerComponent;
    @OptionalComponent
    public HealthComponent healthComponent;
    @OptionalComponent
    public VelocityComponent velocityComponent;
    public HitBoxNode(Entity entity)
    {
        super(entity);
    }

    @Override
    public void onRemove() {
        GameData.world.hitBoxColliders.keySet().remove(this);
    }
}

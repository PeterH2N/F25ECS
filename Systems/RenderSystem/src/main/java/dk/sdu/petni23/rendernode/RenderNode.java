package dk.sdu.petni23.rendernode;

import dk.sdu.petni23.common.components.damage.ThrowComponent;
import dk.sdu.petni23.common.components.health.HealthBarComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.Optional;


public class RenderNode extends Node
{
    public PositionComponent positionComponent;
    public DisplayComponent displayComponent;
    @Optional
    public SpriteComponent spriteComponent;
    @Optional
    public CollisionComponent collisionComponent;
    @Optional
    public HitBoxComponent hitBoxComponent;
    @Optional
    public HealthComponent healthComponent;
    @Optional
    public DirectionComponent directionComponent;
    @Optional
    public ThrowComponent throwComponent;
    @Optional
    public HealthBarComponent healthBarComponent;

    public double getY() {
        return positionComponent.position.y;
    }

    public RenderNode(Entity entity) {
        super(entity);
    }
}

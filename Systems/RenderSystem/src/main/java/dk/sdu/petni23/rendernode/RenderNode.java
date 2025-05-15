package dk.sdu.petni23.rendernode;

import dk.sdu.petni23.common.components.ai.PathFindingComponent;
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
import dk.sdu.petni23.gameengine.node.OptionalComponent;


public class RenderNode extends Node
{
    public PositionComponent positionComponent;
    public DisplayComponent displayComponent;
    @OptionalComponent
    public SpriteComponent spriteComponent;
    @OptionalComponent
    public CollisionComponent collisionComponent;
    @OptionalComponent
    public HitBoxComponent hitBoxComponent;
    @OptionalComponent
    public HealthComponent healthComponent;
    @OptionalComponent
    public DirectionComponent directionComponent;
    @OptionalComponent
    public ThrowComponent throwComponent;
    @OptionalComponent
    public HealthBarComponent healthBarComponent;
    @OptionalComponent
    public PathFindingComponent pathFindingComponent;
    public double getY() {
        double y = positionComponent.position.y;
        //if (spriteComponent != null) y -= spriteComponent.spriteOrigin.y;
        return y;
    }

    public RenderNode(Entity entity) {
        super(entity);
    }

    @Override
    public void onRemove() {

    }

    @Override
    public void onAdd() {

    }
}

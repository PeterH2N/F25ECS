package dk.sdu.petni23.rendernode;

import dk.sdu.petni23.common.components.DisplayComponent;
import dk.sdu.petni23.common.components.collision.BodyComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.hp.DamageComponent;
import dk.sdu.petni23.common.components.hp.HealthComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.SpriteComponent;
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
    public BodyComponent bodyComponent;
    @Optional
    public HitBoxComponent hitBoxComponent;
    @Optional
    public HealthComponent healthComponent;

    public double getZ() {
        return positionComponent.getPosition().y;
    }

    public RenderNode(Entity entity) {
        super(entity);
    }
}

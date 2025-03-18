package dk.sdu.petni23.rendernode;

import dk.sdu.petni23.common.components.collision.BodyComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.animation.SpriteComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.Optional;


public class RenderNode extends Node
{
    public PositionComponent positionComponent;
    public SpriteComponent spriteComponent;
    @Optional
    public BodyComponent bodyComponent;

    public double getZ() {
        return positionComponent.getPosition().y;
    }

    public RenderNode(Entity entity) {
        super(entity);
    }
}

package dk.sdu.petni23.rendernode;

import dk.sdu.petni23.common.components.DirectionComponent;
import dk.sdu.petni23.common.components.PositionComponent;
import dk.sdu.petni23.common.components.SpriteComponent;
import dk.sdu.petni23.common.entity.Entity;
import dk.sdu.petni23.common.node.Node;
import dk.sdu.petni23.common.node.Optional;

public class RenderNode extends Node
{
    public PositionComponent positionComponent;
    public SpriteComponent spriteComponent;
    @Optional
    public DirectionComponent directionComponent;

    public double getZ() {
        return positionComponent.getPosition().y;
    }

    public RenderNode(Entity entity) {
        super(entity);
    }
}

package dk.sdu.petni23.spritenode;

import dk.sdu.petni23.common.components.*;
import dk.sdu.petni23.common.entity.Entity;
import dk.sdu.petni23.common.node.Node;
import dk.sdu.petni23.common.node.Optional;

public class SpriteNode extends Node
{
    public SpriteComponent spriteComponent;
    public PositionComponent positionComponent;
    @Optional
    public VelocityComponent velocityComponent;
    @Optional
    public DirectionComponent directionComponent;

    public SpriteNode(Entity entity) {
        super(entity);
    }
}

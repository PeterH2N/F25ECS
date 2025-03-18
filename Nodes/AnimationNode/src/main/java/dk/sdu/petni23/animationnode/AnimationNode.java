package dk.sdu.petni23.animationnode;

import dk.sdu.petni23.common.components.animation.SpriteComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.Optional;


public class AnimationNode extends Node
{
    public SpriteComponent spriteComponent;
    public PositionComponent positionComponent;
    @Optional
    public VelocityComponent velocityComponent;
    @Optional
    public DirectionComponent directionComponent;

    public AnimationNode(Entity entity) {
        super(entity);
    }
}

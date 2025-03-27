package dk.sdu.petni23.animationnode;

import dk.sdu.petni23.common.components.AnimationComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.Optional;


public class AnimationNode extends Node
{
    private final Entity self;
    public SpriteComponent spriteComponent;
    public AnimationComponent animationComponent;
    @Optional
    public VelocityComponent velocityComponent;
    @Optional
    public DirectionComponent directionComponent;
    @Optional
    public ActionSetComponent actionSetComponent;

    public AnimationNode(Entity entity) {
        super(entity);
        this.self = entity;
    }

    public Entity getEntity() {
        return self;
    }
}

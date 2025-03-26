package dk.sdu.petni23.collisionnode;

import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.Optional;

public class CollisionNode extends Node
{
    public CollisionComponent collisionComponent;
    public PositionComponent positionComponent;
    @Optional
    public VelocityComponent velocityComponent;
    public CollisionNode(Entity entity) {
        super(entity);
    }
}

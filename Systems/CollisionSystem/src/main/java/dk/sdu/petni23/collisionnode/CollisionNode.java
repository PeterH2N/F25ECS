package dk.sdu.petni23.collisionnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.OptionalComponent;

public class CollisionNode extends Node
{
    public CollisionComponent collisionComponent;
    public PositionComponent positionComponent;
    @OptionalComponent
    public VelocityComponent velocityComponent;
    public CollisionNode(Entity entity) {
        super(entity);
    }

    @Override
    public void onRemove() {
        GameData.world.collisionColliders.keySet().remove(this);
        GameData.world.collisionColliderPairs.keySet().removeIf(cp -> cp.c1().node == this || cp.c2().node == this);
    }
}

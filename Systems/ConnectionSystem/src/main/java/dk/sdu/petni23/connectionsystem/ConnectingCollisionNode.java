package dk.sdu.petni23.connectionsystem;

import dk.sdu.petni23.common.components.collision.ConnectingCollisionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class ConnectingCollisionNode extends Node {

    public ConnectingCollisionComponent connectingCollisionComponent;
    public PositionComponent positionComponent;

    @Override
    public void onRemove() {
        ConnectingCollisionSystem.scanWorld();
    }

    @Override
    public void onAdd() {
        ConnectingCollisionSystem.scanWorld();
    }

    public ConnectingCollisionNode(Entity entity) {
        super(entity);
    }
}

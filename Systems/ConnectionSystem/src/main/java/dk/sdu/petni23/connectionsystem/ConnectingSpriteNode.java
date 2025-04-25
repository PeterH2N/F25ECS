package dk.sdu.petni23.connectionsystem;

import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.ConnectingSpriteComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class ConnectingSpriteNode extends Node {

    public ConnectingSpriteComponent connectingSpriteComponent;
    public SpriteComponent spriteComponent;
    public PositionComponent positionComponent;
    public ConnectingSpriteNode(Entity entity) {
        super(entity);
    }

    @Override
    public void onRemove() {
        ConnectingSpriteSystem.scanWorld();
    }

    @Override
    public void onAdd() {
        ConnectingSpriteSystem.scanWorld();
    }
}

package dk.sdu.petni23.gameoversystem;

import dk.sdu.petni23.common.components.gameflow.GameOverComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class GameOverNode extends Node {
    public GameOverComponent gameOverComponent;

    public GameOverNode(Entity entity) {
        super(entity);
    }

    @Override
    public void onRemove() {}

    @Override
    public void onAdd() {}
}

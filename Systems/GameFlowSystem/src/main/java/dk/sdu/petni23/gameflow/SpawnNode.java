package dk.sdu.petni23.gameflow;

import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.common.components.gameflow.SpawnComponent;
import dk.sdu.petni23.gameengine.entity.Entity;

public class SpawnNode extends Node{

    public SpawnComponent spawnComponent;

    public SpawnNode(Entity entity) {
        super(entity);
    }

    @Override
    public void onRemove() {

    }

    @Override
    public void onAdd() {

    }
    
}

package dk.sdu.petni23.respawnsystem;

import dk.sdu.petni23.common.components.RespawnComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class RespawnNode extends Node {

    public RespawnComponent respawnComponent;
    public HealthComponent healthComponent;
    public PositionComponent positionComponent;

    public RespawnNode(Entity entity) {
        super(entity);
    }

    @Override
    public void onAdd() {
    }

    @Override
    public void onRemove() {
    }

}

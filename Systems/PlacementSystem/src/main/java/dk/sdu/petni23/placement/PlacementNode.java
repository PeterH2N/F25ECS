package dk.sdu.petni23.placement;

import dk.sdu.petni23.common.components.PlacementComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class PlacementNode extends Node{

    public PositionComponent positionComponent;
    public PlacementComponent placementComponent;

    public PlacementNode(Entity entity) {
        super(entity);
    }

    @Override
    public void onRemove() {

    }

    @Override
    public void onAdd() {

    }

}

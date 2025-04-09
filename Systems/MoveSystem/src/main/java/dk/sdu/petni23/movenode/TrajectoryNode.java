package dk.sdu.petni23.movenode;

import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.TrajectoryComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class TrajectoryNode extends Node
{
    public PositionComponent positionComponent;
    public TrajectoryComponent trajectoryComponent;
    public TrajectoryNode(Entity entity)
    {
        super(entity);
    }

    @Override
    public void onRemove() {

    }
}

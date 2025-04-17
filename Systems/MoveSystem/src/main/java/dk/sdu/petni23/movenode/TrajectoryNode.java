package dk.sdu.petni23.movenode;

import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.TrajectoryComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.OptionalComponent;

public class TrajectoryNode extends Node
{
    public PositionComponent positionComponent;
    public TrajectoryComponent trajectoryComponent;
    @OptionalComponent
    public DirectionComponent directionComponent;
    public TrajectoryNode(Entity entity)
    {
        super(entity);
    }

    @Override
    public void onRemove() {

    }
}

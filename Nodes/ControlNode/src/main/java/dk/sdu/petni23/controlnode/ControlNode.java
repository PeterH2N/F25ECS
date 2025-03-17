package dk.sdu.petni23.controlnode;

import dk.sdu.petni23.common.components.*;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;


public class ControlNode extends Node
{
    public ControlComponent controlComponent;
    public PositionComponent positionComponent;
    public DirectionComponent directionComponent;
    public VelocityComponent velocityComponent;
    public SpeedComponent speedComponent;
    public ControlNode(Entity entity)
    {
        super(entity);
    }
}

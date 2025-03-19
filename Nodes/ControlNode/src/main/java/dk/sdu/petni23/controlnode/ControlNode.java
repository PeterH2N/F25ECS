package dk.sdu.petni23.controlnode;

import dk.sdu.petni23.common.components.ControlComponent;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.SpeedComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.Optional;


public class ControlNode extends Node
{
    public ControlComponent controlComponent;
    public PositionComponent positionComponent;
    public DirectionComponent directionComponent;
    public VelocityComponent velocityComponent;
    public SpeedComponent speedComponent;
    @Optional
    public ActionSetComponent actionSetComponent;
    public ControlNode(Entity entity)
    {
        super(entity);
    }
}

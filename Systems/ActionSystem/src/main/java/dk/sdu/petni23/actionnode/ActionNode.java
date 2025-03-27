package dk.sdu.petni23.actionnode;

import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.life.LayerComponent;
import dk.sdu.petni23.common.components.life.StrengthComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.Optional;

public class ActionNode extends Node
{
    public ActionSetComponent actionSetComponent;
    public LayerComponent layerComponent;
    @Optional
    public PositionComponent positionComponent;
    @Optional
    public DirectionComponent directionComponent;
    @Optional
    public StrengthComponent strengthComponent;

    public ActionNode(Entity entity)
    {
        super(entity);
    }
}

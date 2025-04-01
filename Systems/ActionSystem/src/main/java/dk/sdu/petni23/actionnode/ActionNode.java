package dk.sdu.petni23.actionnode;

import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.damage.AttackComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.Optional;

public class ActionNode extends Node
{
    public ActionSetComponent actionSetComponent;
    /*public LayerComponent layerComponent;
    @Optional
    public PositionComponent positionComponent;
    @Optional
    public DirectionComponent directionComponent;
    @Optional
    public AttackComponent attackComponent;*/

    public ActionNode(Entity entity)
    {
        super(entity);
    }
}

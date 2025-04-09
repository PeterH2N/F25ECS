package dk.sdu.petni23.actionnode;

import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class ActionNode extends Node
{
    public ActionSetComponent actionSetComponent;

    public ActionNode(Entity entity)
    {
        super(entity);
    }

    @Override
    public void onRemove() {

    }
}

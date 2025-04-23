package dk.sdu.petni23.actionnode;

import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.damage.AttackComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.OptionalComponent;

public class ActionNode extends Node
{
    public ActionSetComponent actionSetComponent;
    @OptionalComponent
    public AttackComponent attackComponent;

    public ActionNode(Entity entity)
    {
        super(entity);
    }

    @Override
    public void onRemove() {

    }

    @Override
    public void onAdd() {

    }
}

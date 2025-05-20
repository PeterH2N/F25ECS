package dk.sdu.petni23.actionnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;

public class ActionSystem extends ISystem
{
    @Override
    public void update(double deltaTime)
    {
        for (ActionNode node : Engine.getNodes(ActionNode.class)) {
            long now = GameData.getCurrentMillis();
            double speed = 1;
            if (node.attackComponent != null) speed = node.attackComponent.speed;
            if (now <= node.actionSetComponent.lastActionTime + (node.actionSetComponent.lastAction.duration / speed)) {
                if (now > node.actionSetComponent.lastActionTime + (node.actionSetComponent.lastAction.delay / speed)) {
                    if (node.actionSetComponent.lastAction.onDispatch != null && !node.actionSetComponent.hasDispatched) {
                        node.actionSetComponent.hasDispatched = true;
                        node.actionSetComponent.lastAction.onDispatch.dispatch(node);
                    }
                }
            } else node.actionSetComponent.hasDispatched = false;
        }
    }

    @Override
    public int getPriority()
    {
        return Priority.PREPROCESSING.get();
    }
}

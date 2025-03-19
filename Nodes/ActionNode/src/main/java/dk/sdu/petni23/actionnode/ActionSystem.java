package dk.sdu.petni23.actionnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;

public class ActionSystem implements ISystem
{
    @Override
    public void update(double deltaTime)
    {
        for (ActionNode node : Engine.getNodes(ActionNode.class)) {
            long now = GameData.getCurrentMillis();
            if (node.actionSetComponent.isPerformingAction()) {
                if (now > node.actionSetComponent.lastActionTime + node.actionSetComponent.lastAction.delay) {
                    if (node.actionSetComponent.lastAction.spi != null && !node.actionSetComponent.hasDispatched) {
                        node.actionSetComponent.hasDispatched = true;
                        Engine.addEntity(node.actionSetComponent.lastAction.spi.create(node));
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

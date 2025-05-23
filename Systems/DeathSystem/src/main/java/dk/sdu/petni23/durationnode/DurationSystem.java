package dk.sdu.petni23.durationnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;

public class DurationSystem extends ISystem {
    @Override
    public void update(double deltaTime)
    {
        for (DurationNode node : Engine.getNodes(DurationNode.class)) {
            if (GameData.getCurrentMillis() >= node.durationComponent.createdAt + node.durationComponent.lifetime) {
                if (node.durationComponent.onDeath != null)
                    node.durationComponent.onDeath.dispatch(node);
                Engine.removeEntity(node.getEntityID());
            }
        }
    }

    @Override
    public int getPriority()
    {
        return Priority.POSTPROCESSING.get();
    }
}

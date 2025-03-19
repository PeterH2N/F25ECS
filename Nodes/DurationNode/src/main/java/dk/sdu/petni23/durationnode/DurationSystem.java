package dk.sdu.petni23.durationnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;

public class DurationSystem implements ISystem
{
    @Override
    public void update(double deltaTime)
    {
        for (DurationNode node : Engine.getNodes(DurationNode.class)) {
            if (GameData.getCurrentMillis() > node.lifeTimeComponent.createdAt + node.lifeTimeComponent.lifetime)
                Engine.removeEntity(node.getEntityID());
        }
    }

    @Override
    public int getPriority()
    {
        return Priority.POSTPROCESSING.get();
    }
}

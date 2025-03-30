package dk.sdu.petni23.healthnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;

public class HealthSystem implements ISystem
{
    @Override
    public void update(double deltaTime)
    {
        for (HealthNode node : Engine.getNodes(HealthNode.class)) {
            if (node.healthComponent.health <= 0) {
                if (node.healthComponent.onDeath != null)
                    node.healthComponent.onDeath.dispatch(node);
                if (node.lootComponent != null) {
                    int numDrops = GameData.random.nextInt(node.lootComponent.minDrop, node.lootComponent.maxDrop + 1);
                    for (int i = 0; i < numDrops; i++) {
                        node.lootComponent.drop.dispatch(node);
                    }
                }
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

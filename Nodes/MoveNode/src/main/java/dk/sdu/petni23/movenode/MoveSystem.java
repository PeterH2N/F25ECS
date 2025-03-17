package dk.sdu.petni23.movenode;

import dk.sdu.petni23.common.Engine;
import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.PositionComponent;
import dk.sdu.petni23.common.services.IProcessingSystem;

public class MoveSystem implements IProcessingSystem
{
    @Override
    public void update(double deltaTime)
    {
        for (MoveNode node : Engine.getNodes(MoveNode.class)) {
            node.position.getPosition().add(node.velocity.getVelocity().getMultiplied(deltaTime));
            containWithinWorldBounds(node.position);
        }
    }


    void containWithinWorldBounds(PositionComponent position) {
        var pos = position.getPosition();
        double halfExtent = (double) GameData.worldSize / 2;
        pos.x = Math.clamp(pos.x, -halfExtent, halfExtent);
        pos.y = Math.clamp(pos.y, -halfExtent, halfExtent);
    }
}

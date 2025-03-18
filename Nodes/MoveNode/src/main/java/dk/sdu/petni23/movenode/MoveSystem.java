package dk.sdu.petni23.movenode;


import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.PositionComponent;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;


public class MoveSystem implements ISystem
{
    @Override
    public void update(double deltaTime)
    {
        for (MoveNode node : Engine.getNodes(MoveNode.class)) {
            node.position.getPosition().add(node.velocity.getVelocity().getMultiplied(deltaTime));
            containWithinWorldBounds(node.position);
        }
    }

    @Override
    public int getPriority()
    {
        return Priority.PROCESSING.get();
    }


    void containWithinWorldBounds(PositionComponent position) {
        var pos = position.getPosition();
        double halfExtent = (double) GameData.worldSize / 2;
        pos.x = Math.clamp(pos.x, -halfExtent, halfExtent);
        pos.y = Math.clamp(pos.y, -halfExtent, halfExtent);
    }
}

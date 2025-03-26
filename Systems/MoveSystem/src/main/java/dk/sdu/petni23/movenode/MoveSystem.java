package dk.sdu.petni23.movenode;


import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPhysicsSystem;


public class MoveSystem implements IPhysicsSystem
{
    @Override
    public void step(double deltaTime)
    {
        for (MoveNode node : Engine.getNodes(MoveNode.class)) {
            node.position.position.add(node.velocity.velocity.getMultiplied(deltaTime));
            containWithinWorldBounds(node.position);
        }
    }

    void containWithinWorldBounds(PositionComponent position) {
        var pos = position.position;
        double halfExtent = (double) GameData.worldSize / 2;
        pos.x = Math.clamp(pos.x, -halfExtent, halfExtent);
        pos.y = Math.clamp(pos.y, -halfExtent, halfExtent);
    }
}

package dk.sdu.petni23.movenode;


import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPhysicsSystem;


public class MoveSystem implements IPhysicsSystem
{
    void containWithinWorldBounds(PositionComponent position) {
        var pos = position.getPosition();
        double halfExtent = (double) GameData.worldSize / 2;
        pos.x = Math.clamp(pos.x, -halfExtent, halfExtent);
        pos.y = Math.clamp(pos.y, -halfExtent, halfExtent);
    }

    @Override
    public void preUpdate()
    {

    }

    @Override
    public void step(double deltaTime)
    {
        for (MoveNode node : Engine.getNodes(MoveNode.class)) {
            node.position.getPosition().add(node.velocity.getVelocity().getMultiplied(deltaTime));
            containWithinWorldBounds(node.position);
        }
    }
}

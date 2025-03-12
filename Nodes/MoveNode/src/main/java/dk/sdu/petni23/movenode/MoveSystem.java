package dk.sdu.petni23.movenode;

import dk.sdu.petni23.common.Engine;
import dk.sdu.petni23.common.services.ISystem;

public class MoveSystem implements ISystem
{
    @Override
    public void update(double deltaTime)
    {
        for (MoveNode node : Engine.getNodes(MoveNode.class)) {
            node.position.getPosition().add(node.velocity.getVelocity().getMultiplied(deltaTime));
        }
    }

    @Override
    public void start()
    {

    }
}

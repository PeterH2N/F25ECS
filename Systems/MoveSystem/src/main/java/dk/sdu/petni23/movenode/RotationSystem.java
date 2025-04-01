package dk.sdu.petni23.movenode;

import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;

public class RotationSystem implements ISystem
{
    @Override
    public void update(double deltaTime)
    {
        for (RotateNode node : Engine.getNodes(RotateNode.class)){
            node.directionComponent.dir.rotateBy(node.angularMomentumComponent.angularMomentum * deltaTime);
        }
    }

    @Override
    public int getPriority()
    {
        return Priority.PROCESSING.get();
    }
}

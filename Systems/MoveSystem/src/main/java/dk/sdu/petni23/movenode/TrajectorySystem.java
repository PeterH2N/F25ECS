package dk.sdu.petni23.movenode;

import dk.sdu.petni23.common.components.movement.TrajectoryComponent;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;

public class TrajectorySystem implements ISystem
{
    @Override
    public void update(double deltaTime)
    {
        for (var node : Engine.getNodes(TrajectoryNode.class)) {
            node.trajectoryComponent.t += deltaTime;

        }
    }

    @Override
    public int getPriority()
    {
        return Priority.PROCESSING.get();
    }

    double y(TrajectoryComponent trajectoryComponent) {
        double a = trajectoryComponent.a;
        double b = trajectoryComponent.b;
        double t = trajectoryComponent.t;
        return a * t * t + b * t;
    }
}

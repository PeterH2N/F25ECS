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
            double t = node.trajectoryComponent.t;
            if (t >= node.trajectoryComponent.d) {
                node.trajectoryComponent.onEnd.dispatch(node);
                Engine.removeEntity(node.getEntityID());
            }

            node.trajectoryComponent.t += deltaTime * 6;

            var start = node.trajectoryComponent.start;

            var dir = node.trajectoryComponent.dir;
            node.positionComponent.position.set(start.getAdded(dir.getMultiplied(t)));
            node.positionComponent.position.y += y(node.trajectoryComponent);


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

    double slope(TrajectoryComponent trajectoryComponent) {
        double a = trajectoryComponent.a;
        double b = trajectoryComponent.b;
        double t = trajectoryComponent.t;
        return a * t + b;
    }
}

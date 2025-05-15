package dk.sdu.petni23.movenode;

import dk.sdu.petni23.common.components.movement.TrajectoryComponent;
import dk.sdu.petni23.common.util.Vector2D;
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
            var dir = node.trajectoryComponent.dir;

            node.trajectoryComponent.t += deltaTime * node.trajectoryComponent.speed;

            var start = node.trajectoryComponent.start;

            node.positionComponent.position.set(start.getAdded(dir.getMultiplied(t)));
            node.positionComponent.position.y += y(node.trajectoryComponent);

            if (node.trajectoryComponent.rotateWithSlope && node.directionComponent != null) {
                double slope = slope(node.trajectoryComponent);
                if (dir.x < 0) slope = -slope;
                Vector2D slopeDir = new Vector2D(1, slope);
                slopeDir.rotateBy(dir.getAngle());

                node.directionComponent.dir.set(new Vector2D(dir.x, slopeDir.y).getNormalized());
            }

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
        return 2 * a * t + b;
    }
}

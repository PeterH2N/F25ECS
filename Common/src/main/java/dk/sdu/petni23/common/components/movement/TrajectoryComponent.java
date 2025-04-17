package dk.sdu.petni23.common.components.movement;

import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.common.util.Vector2D;

public class TrajectoryComponent extends Component
{
    public final Vector2D start;
    public final Vector2D end;

    public final Vector2D dir;
    public double height;
    public double t = 0;
    public final double a;
    public final double b;
    public final double d;
    public final double speed;
    public final Dispatch onEnd;
    public boolean rotateWithSlope = false;

    public TrajectoryComponent(Vector2D start, Vector2D end, double height, double speed, Dispatch onEnd)
    {
        this.start = new Vector2D(start);
        this.end = new Vector2D(end);
        this.height = height;
        this.onEnd = onEnd;
        this.speed = speed;
        dir = end.getSubtracted(start);

        d = dir.getLength();
        dir.normalize();

        b = height / (d * 0.25);
        a = -b / d;
    }
    public TrajectoryComponent(Vector2D start, Vector2D end, double height, Dispatch onEnd) {
        this(start, end, height, 6, onEnd);
    }
}

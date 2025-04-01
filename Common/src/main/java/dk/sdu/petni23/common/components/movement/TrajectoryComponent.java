package dk.sdu.petni23.common.components.movement;

import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.util.Vector2D;

public class TrajectoryComponent extends Component
{
    public final Vector2D start;
    public final Vector2D end;

    public final Vector2D dir;
    public double height;
    public double t = 0;
    public final double a;
    public final double b;
    public final Dispatch onEnd;

    public TrajectoryComponent(Vector2D start, Vector2D end, double height, Dispatch onEnd)
    {
        this.start = new Vector2D(start);
        this.end = new Vector2D(end);
        this.height = height;
        this.onEnd = onEnd;
        dir = end.getSubtracted(start);

        double d = dir.getLength();
        b = height / (d * 0.25);
        a = -b / d;
    }
}

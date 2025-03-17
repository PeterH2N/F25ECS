package dk.sdu.petni23.common.components;

import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Component;

public class DirectionComponent extends Component
{
    private final Vector2D dir = new Vector2D(1,0);

    public void setRotation(double r) {

        dir.set(Math.cos(r), Math.sin(r));
    }

    public double getRotation() {
        return Math.atan2(dir.y, dir.x);
    }

    public void setDirection(Vector2D v) {
        dir.set(v.getNormalized());
    }

    public Vector2D getDirection() {
        return dir;
    }
}

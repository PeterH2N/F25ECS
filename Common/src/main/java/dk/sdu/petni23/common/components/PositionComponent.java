package dk.sdu.petni23.common.components;

import dk.sdu.petni23.common.util.Vector2D;

public class PositionComponent extends Component
{
    private Vector2D position = new Vector2D(0,0);


    public Vector2D getPosition()
    {
        return position;
    }

    public void setPosition(Vector2D v) {
        position.set(v);
    }
    public void setPosition(double x, double y) {
        position.set(x, y);
    }
}

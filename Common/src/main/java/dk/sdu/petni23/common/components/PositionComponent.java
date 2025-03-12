package dk.sdu.petni23.common.components;

import dk.sdu.petni23.common.util.Vector2D;

public class PositionComponent extends Component
{
    private Vector2D position = new Vector2D(0,0);
    private double rotation = 0; // radians

    public Vector2D getPosition()
    {
        return position;
    }

    public void setPosition(Vector2D position)
    {
        this.position = position;
    }

    public double getRotation()
    {
        return rotation;
    }

    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }
}

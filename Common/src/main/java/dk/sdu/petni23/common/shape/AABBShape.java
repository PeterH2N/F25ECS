package dk.sdu.petni23.common.shape;

import dk.sdu.petni23.common.util.Vector2D;

public class AABBShape extends Shape
{
    public double width;
    public double height;

    @Override
    public Vector2D getBB()
    {
        return new Vector2D(width, height);
    }
}

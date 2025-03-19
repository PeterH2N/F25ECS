package dk.sdu.petni23.common.components.collision;

import dk.sdu.petni23.common.shape.Shape;

public class BodyComponent extends HasShapeComponent
{
    private Shape shape;

    @Override
    public Shape getShape()
    {
        return shape;
    }

    public void setShape(Shape shape)
    {
        this.shape = shape;
    }
}

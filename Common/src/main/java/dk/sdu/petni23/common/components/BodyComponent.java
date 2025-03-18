package dk.sdu.petni23.common.components;

import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.gameengine.Component;

public class BodyComponent extends Component
{
    private Shape shape;

    public Shape getShape()
    {
        return shape;
    }

    public void setShape(Shape shape)
    {
        this.shape = shape;
    }
}

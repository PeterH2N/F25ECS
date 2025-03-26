package dk.sdu.petni23.common.components.collision;

import dk.sdu.petni23.common.shape.Shape;

public class HitBoxComponent extends HasShapeComponent
{
    public final Shape hitBox;
    public double yOffset = 0;

    public HitBoxComponent(Shape shape) {
        this.hitBox = shape;
    }
    @Override
    public Shape getShape() {
        return hitBox;
    }
}

package dk.sdu.petni23.common.components.collision;

import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.util.Vector2D;

public class HitBoxComponent extends HasShapeComponent
{
    public final Shape hitBox;

    public HitBoxComponent(Shape shape, Vector2D offset) {
        this.hitBox = shape;
        this.offset.set(offset);
    }
    public HitBoxComponent(Shape shape) {
        this.hitBox = shape;
    }
    @Override
    public Shape getShape() {
        return hitBox;
    }
}

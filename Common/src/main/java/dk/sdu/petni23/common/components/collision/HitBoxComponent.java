package dk.sdu.petni23.common.components.collision;

import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.util.Vector2D;

public class HitBoxComponent extends HasShapeComponent
{
    public Shape hitBox = null;

    public HitBoxComponent(Shape shape, Vector2D offset) {
        this.hitBox = shape;
        this.offset.set(offset);
    }
    public HitBoxComponent(Shape shape) {
        this.hitBox = shape;
    }
    public HitBoxComponent(){}
    @Override
    public Shape getShape() {
        return hitBox;
    }
}

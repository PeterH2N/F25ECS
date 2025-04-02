package dk.sdu.petni23.common.components.collision;

import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.util.Vector2D;

public class CollisionComponent extends HasShapeComponent
{
    public Shape shape;

    public CollisionComponent(Shape shape, Vector2D offset) {
        this.shape = shape;
        this.offset.set(offset);
    }

    public CollisionComponent(Shape shape) {
        this.shape = shape;
    }
    @Override
    public Shape getShape()
    {
        return shape;
    }
}

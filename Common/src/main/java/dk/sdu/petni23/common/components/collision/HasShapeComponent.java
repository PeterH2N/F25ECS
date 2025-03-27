package dk.sdu.petni23.common.components.collision;

import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.gameengine.util.Vector2D;
import dk.sdu.petni23.gameengine.Component;

public abstract class HasShapeComponent extends Component
{
    public abstract Shape getShape();
    public final Vector2D offset = new Vector2D(0,0);
}

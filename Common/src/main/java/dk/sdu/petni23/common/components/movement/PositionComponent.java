package dk.sdu.petni23.common.components.movement;

import dk.sdu.petni23.gameengine.util.Vector2D;
import dk.sdu.petni23.gameengine.Component;

public class PositionComponent extends Component
{
    public final Vector2D position = new Vector2D(0, 0);

    public PositionComponent()
    {
    }

    public PositionComponent(Vector2D p) {
        position.set(p);
    }
}

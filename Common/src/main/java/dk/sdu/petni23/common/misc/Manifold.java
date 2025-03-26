package dk.sdu.petni23.common.misc;

import dk.sdu.petni23.common.components.collision.HasShapeComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.node.Node;

public class Manifold
{
    public Node a, b;
    public Shape aShape, bShape;
    public PositionComponent aPos, bPos;
    public Vector2D normal;
    public double penetration;
    public boolean collide;

    public Manifold(Node a, Node b) {
        this.a = a;
        this.b = b;
        var aComp = a.getComponent(HasShapeComponent.class);
        var bComp = b.getComponent(HasShapeComponent.class);
        assert (aComp != null && bComp != null);
        aShape = aComp.getShape();
        bShape = bComp.getShape();
        aPos = a.getComponent(PositionComponent.class);
        bPos = b.getComponent(PositionComponent.class);
        assert (aShape != null && bShape != null && aPos != null && bPos != null);
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Manifold m))
            return false;

        return (m.a == a && m.b == b) || (m.b == a && m.a == b);
    }
}

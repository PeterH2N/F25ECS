package dk.sdu.petni23.common.misc;

import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class Manifold
{
    public Node a, b;
    public Vector2D normal;
    public double penetration;
    public boolean collide;

    public Manifold(Node a, Node b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Manifold m))
            return false;

        return (m.a == a && m.b == b) || (m.b == a && m.a == b);
    }
}

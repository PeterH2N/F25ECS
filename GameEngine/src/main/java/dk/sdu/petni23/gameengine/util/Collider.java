package dk.sdu.petni23.gameengine.util;

import dk.sdu.petni23.gameengine.util.Vector2D;
import dk.sdu.petni23.gameengine.node.Node;

import java.util.ArrayList;
import java.util.List;

public class Collider {
    public List<Vector2D> cells = new ArrayList<>();
    public final Node node;

    public Collider(Node node)
    {
        this.node = node;
    }
}

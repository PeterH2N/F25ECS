package dk.sdu.petni23.common.misc;

import dk.sdu.petni23.gameengine.node.Node;

import java.util.List;

public class GridNode
{
    public final List<Integer> indices;

    public final Node node;
    public GridNode(List<Integer> indices, Node node)
    {
        this.indices = indices;
        this.node = node;
    }
}

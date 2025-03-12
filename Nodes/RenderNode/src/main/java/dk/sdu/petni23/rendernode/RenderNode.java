package dk.sdu.petni23.rendernode;

import dk.sdu.petni23.common.components.DisplayComponent;
import dk.sdu.petni23.common.components.PositionComponent;
import dk.sdu.petni23.common.entity.Entity;
import dk.sdu.petni23.common.node.Node;

public class RenderNode extends Node
{
    public DisplayComponent display;
    public PositionComponent position;

    public RenderNode(Entity entity) {
        super(entity);
    }
}

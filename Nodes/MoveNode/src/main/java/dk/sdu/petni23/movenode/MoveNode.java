package dk.sdu.petni23.movenode;

import dk.sdu.petni23.common.components.Component;
import dk.sdu.petni23.common.components.PositionComponent;
import dk.sdu.petni23.common.components.VelocityComponent;
import dk.sdu.petni23.common.entity.Entity;
import dk.sdu.petni23.common.node.Node;

import java.util.List;

public class MoveNode extends Node
{
    public PositionComponent position;
    public VelocityComponent velocity;

    public MoveNode(Entity entity) {
        super(entity);
    }
}

package dk.sdu.petni23.movenode;

import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.Optional;


public class MoveNode extends Node
{
    public PositionComponent position;
    public VelocityComponent velocity;

    public MoveNode(Entity entity) {
        super(entity);
    }
}

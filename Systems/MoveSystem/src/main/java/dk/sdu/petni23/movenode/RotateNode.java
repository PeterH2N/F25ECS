package dk.sdu.petni23.movenode;

import dk.sdu.petni23.common.components.movement.AngularMomentumComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class RotateNode extends Node
{
    public DirectionComponent directionComponent;
    public AngularMomentumComponent angularMomentumComponent;
    public RotateNode(Entity entity)
    {
        super(entity);
    }

    @Override
    public void onRemove() {

    }

    @Override
    public void onAdd() {

    }
}

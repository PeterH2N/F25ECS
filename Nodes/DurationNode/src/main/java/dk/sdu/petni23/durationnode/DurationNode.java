package dk.sdu.petni23.durationnode;

import dk.sdu.petni23.common.components.LifeTimeComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class DurationNode extends Node
{
    public LifeTimeComponent lifeTimeComponent;
    public DurationNode(Entity entity)
    {
        super(entity);
    }
}

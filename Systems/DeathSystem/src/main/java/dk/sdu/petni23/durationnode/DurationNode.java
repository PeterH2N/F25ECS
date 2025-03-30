package dk.sdu.petni23.durationnode;

import dk.sdu.petni23.common.components.life.DurationComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class DurationNode extends Node
{
    public DurationComponent durationComponent;
    public DurationNode(Entity entity)
    {
        super(entity);
    }
}

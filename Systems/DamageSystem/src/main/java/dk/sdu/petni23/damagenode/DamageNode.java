package dk.sdu.petni23.damagenode;

import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.DamageComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class DamageNode extends Node
{
    public DamageComponent damageComponent;
    public HitBoxComponent hitBoxComponent;
    public LayerComponent layerComponent;

    public DamageNode(Entity entity)
    {
        super(entity);
    }

    @Override
    public void onRemove() {

    }
}

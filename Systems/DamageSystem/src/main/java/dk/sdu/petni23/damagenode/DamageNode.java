package dk.sdu.petni23.damagenode;

import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.life.DamageComponent;
import dk.sdu.petni23.common.components.life.LayerComponent;
import dk.sdu.petni23.common.components.life.StrengthComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.Optional;

public class DamageNode extends Node
{
    public DamageComponent damageComponent;
    public HitBoxComponent hitBoxComponent;
    public LayerComponent layerComponent;
    @Optional
    public StrengthComponent strengthComponent;

    public DamageNode(Entity entity)
    {
        super(entity);
    }
}

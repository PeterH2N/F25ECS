package dk.sdu.petni23.npcnode;

import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.ai.JobComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.inventory.InventoryComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.OptionalComponent;

public class NPCNode extends Node {
    public SpriteComponent spriteComponent;
    public VelocityComponent velocityComponent;
    public InventoryComponent inventoryComponent;
    public AIComponent aiComponent;

    public NPCNode(Entity entity) {
        super(entity);
    }

    @OptionalComponent
    public JobComponent jobComponent;

    @OptionalComponent
    public PositionComponent positionComponent;

    @Override
    public void onAdd() {
    }

    @Override
    public void onRemove() {
    }

}

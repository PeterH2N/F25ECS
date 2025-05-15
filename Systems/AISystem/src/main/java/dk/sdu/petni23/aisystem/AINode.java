package dk.sdu.petni23.aisystem;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.ai.PathFindingComponent;
import dk.sdu.petni23.common.components.ai.WorkerComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.AttackComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.damage.ThrowComponent;
import dk.sdu.petni23.common.components.inventory.InventoryComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.OptionalComponent;

public class AINode extends Node {
    public AIComponent aiComponent;
    public PositionComponent positionComponent;
    public LayerComponent layerComponent;
    @OptionalComponent
    public WorkerComponent workerComponent;
    @OptionalComponent
    public InventoryComponent inventoryComponent;
    @OptionalComponent
    public HitBoxComponent hitBoxComponent;
    @OptionalComponent
    public VelocityComponent velocityComponent;
    @OptionalComponent
    public PathFindingComponent pathFindingComponent;
    @OptionalComponent
    public DirectionComponent directionComponent;
    @OptionalComponent
    public ThrowComponent throwComponent;
    @OptionalComponent
    public ActionSetComponent actionSetComponent;
    @OptionalComponent
    public AttackComponent attackComponent;

    public AINode(Entity entity) {
        super(entity);
    }

    @Override
    public void onRemove() {
        if (layerComponent == null) return;
        AISystem.nodes.get(layerComponent.layer.value()).remove(this);
    }

    @Override
    public void onAdd() {
        AISystem.nodes.get(layerComponent.layer.value()).add(this);
    }
}

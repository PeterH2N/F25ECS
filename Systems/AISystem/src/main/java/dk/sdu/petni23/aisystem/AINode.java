package dk.sdu.petni23.aisystem;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.ai.PathFindingComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.AttackComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.damage.ThrowComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.OptionalComponent;

public class AINode extends Node {
    public AIComponent aiComponent;
    public PositionComponent positionComponent;
    public LayerComponent layerComponent;
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
        AISystem.nodes.get(layerComponent.layer.value()).add(this);
        if (entity == GameData.world.nexus) AISystem.nexus = this;
    }

    @Override
    public void onRemove() {
        if (layerComponent == null) return;
        AISystem.nodes.get(layerComponent.layer.value()).remove(this);
        if (AISystem.nexus != null && this.getEntityID() == AISystem.nexus.getEntityID()) AISystem.nexus = null;
    }
}

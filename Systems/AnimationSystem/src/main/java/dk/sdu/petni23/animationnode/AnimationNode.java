package dk.sdu.petni23.animationnode;

import dk.sdu.petni23.common.components.ai.WorkerComponent;
import dk.sdu.petni23.common.components.damage.AttackComponent;
import dk.sdu.petni23.common.components.inventory.InventoryComponent;
import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.node.OptionalComponent;


public class AnimationNode extends Node
{
    public SpriteComponent spriteComponent;
    public AnimationComponent animationComponent;
    @OptionalComponent
    public VelocityComponent velocityComponent;
    @OptionalComponent
    public DirectionComponent directionComponent;
    @OptionalComponent
    public ActionSetComponent actionSetComponent;
    @OptionalComponent
    public AttackComponent attackComponent;
    @OptionalComponent
    public WorkerComponent workerComponent;

    public AnimationNode(Entity entity) {
        super(entity);
    }

    @Override
    public void onRemove() {

    }

    @Override
    public void onAdd() {

    }
}

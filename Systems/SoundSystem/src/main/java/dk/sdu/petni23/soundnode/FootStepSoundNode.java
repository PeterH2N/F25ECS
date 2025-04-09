package dk.sdu.petni23.soundnode;

import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.sound.FootstepSoundComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class FootStepSoundNode extends Node
{
    public FootstepSoundComponent footstepSoundComponent;
    public SpriteComponent spriteComponent;
    public VelocityComponent velocityComponent;
    public FootStepSoundNode(Entity entity)
    {
        super(entity);
    }

    @Override
    public void onRemove() {

    }
}

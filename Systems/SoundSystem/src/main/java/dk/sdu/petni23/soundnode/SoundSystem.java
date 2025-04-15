package dk.sdu.petni23.soundnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.gameengine.services.ISystem;

public class SoundSystem implements ISystem {

    @Override
    public void update(double deltaTime) {

        // Handle regular SoundComponents
        for (SoundNode node : Engine.getNodes(SoundNode.class)) {
            SoundComponent soundComponent = node.soundComponent;
            double volume = 1;
            double dist = GameData.camera.center.distance(soundComponent.position);
            if (dist > 1) {
                volume = Math.min(volume * (1 / (dist * dist * 0.01)), 1);
            }

            System.out.println("🎧 Triggered sound: " +  soundComponent.soundEffect + " (volume: " + volume + ")");
            SoundManager.playSound(soundComponent.soundEffect, soundComponent.delay, volume);
            Engine.removeEntity(node.getEntityID());
        }

        for (FootStepSoundNode node : Engine.getNodes(FootStepSoundNode.class)) {
            if (node.velocityComponent.velocity.equals(Vector2D.ZERO))
                continue;

            int currentFrame = node.spriteComponent.column;
            var footstep = node.footstepSoundComponent;

            if (footstep.lastFrame != currentFrame) {
                if (footstep.triggerFrames.contains(currentFrame)) {
                    System.out.println("👟 Triggering step sound: " + footstep.soundEffect);
                    SoundManager.playSound(footstep.soundEffect, 0);
                }

                footstep.lastFrame = currentFrame;
            }
        }

    }

    @Override
    public int getPriority() {
        return 5; // Adjust priority if needed
    }

}

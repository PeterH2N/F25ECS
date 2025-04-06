package dk.sdu.petni23.soundnode;

import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.gameengine.services.ISystem;

public class SoundSystem implements ISystem {
    private final SoundManager soundManager = new SoundManager();
    private boolean preloaded = false;

    @Override
    public void update(double deltaTime) {
        // Preload sounds if not already done
        if (!preloaded) {
            soundManager.preloadSounds("click1", "tree_hit1", "footstep_player", "woosh1", "woosh2");
            preloaded = true;
        }

        long now = System.currentTimeMillis();

        // Handle regular SoundComponents
        for (SoundNode node : Engine.getNodes(SoundNode.class)) {
            SoundComponent soundComponent = node.soundComponent;

            if (now >= soundComponent.playAt) {
                System.out.println(
                        "🎧 Triggered sound: " + soundComponent.action + " (volume: " + soundComponent.volume + ")");
                soundManager.playSound(soundComponent.action, 0, soundComponent.volume);
                Engine.removeEntity(node.getEntityID());
            }
        }

        for (FootStepSoundNode node : Engine.getNodes(FootStepSoundNode.class)) {
            if (node.velocityComponent.velocity.equals(Vector2D.ZERO))
                continue;

            int currentFrame = node.spriteComponent.column;
            var footstep = node.footstepSoundComponent;

            if (footstep.lastFrame != currentFrame) {
                if (footstep.triggerFrames.contains(currentFrame)) {
                    System.out.println("👟 Triggering step sound: " + footstep.sound);
                    soundManager.playSound(footstep.sound, 0);
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

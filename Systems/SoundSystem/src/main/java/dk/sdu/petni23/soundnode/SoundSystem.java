package dk.sdu.petni23.soundnode;

import dk.sdu.petni23.gameengine.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.gameengine.services.ISystem;

public class SoundSystem implements ISystem {
    private final SoundManager soundManager = new SoundManager();

    @Override
    public void update(double deltaTime) {

        boolean preloaded = false;
        // Preload sounds if not already done
        if (!preloaded) {
            soundManager.preloadSounds("click1", "tree_hit1", "footstep_player", "woosh1", "woosh2");
            preloaded = true;
        }
        
        long now = System.currentTimeMillis();

        // Handle regular SoundComponents
        for (SoundNode node : Engine.getNodes(SoundNode.class)) {
            SoundComponent soundComponent = node.soundComponent;

            if (soundComponent.triggered && now >= soundComponent.playAt) {
                System.out.println("🎧 Triggered sound: " + soundComponent.action + " (volume: " + soundComponent.volume + ")");
                soundManager.playSound(soundComponent.action, 0, soundComponent.volume);
                soundComponent.triggered = false;
            }
        }

        // Handle FootstepSoundComponents
        for (FootStepSoundNode node : Engine.getNodes(FootStepSoundNode.class)) {
            if (node.velocityComponent.velocity.equals(Vector2D.ZERO)) continue;

            int currentFrame = node.spriteComponent.column;

            if (node.footstepSoundComponent.lastFrame != currentFrame) {
                if (currentFrame == 1 || currentFrame == 4) {
                    var footstep = node.footstepSoundComponent;
                        System.out.println("👟 Triggering step sound: " + footstep.sound);
                        soundManager.playSound(footstep.sound, 0);
                }

                node.footstepSoundComponent.lastFrame = currentFrame;
            }
            
        }
    }

    @Override
    public int getPriority() {
        return 5; // Adjust priority if needed
    }
    
}

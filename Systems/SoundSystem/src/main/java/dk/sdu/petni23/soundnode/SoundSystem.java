package dk.sdu.petni23.soundnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.gameengine.Engine;


import java.util.List;

public class SoundSystem implements ISystem {
    private final SoundManager soundManager = new SoundManager();

    @Override
    public void update(double deltaTime) {
        var nodes = Engine.getNodes(SoundNode.class);
        System.out.println("🔎 SoundNodes found: " + nodes.size()); // ← log node count
    
        for (SoundNode node : nodes) {
            SoundComponent soundComponent = node.soundComponent;
    
            if (soundComponent.triggered) {
                System.out.println("🎧 Triggered sound: " + soundComponent.action);
                soundManager.playSound(soundComponent.action, 0);
                soundComponent.triggered = false;
            }
        }
    }
    
    
    
    

    @Override
    public int getPriority() {
        return 5; // Adjust priority if needed
    }
}

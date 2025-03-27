package dk.sdu.petni23.common.util;

import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;

public class Sound {
    public static void emitSound(String action) {
        Entity soundEntity = new Entity();
        soundEntity.add(new SoundComponent(action));
        Engine.addEntity(soundEntity);
    }

    public static void playUISound(String soundName) {
        System.out.println("🔊 Attempting to play UI sound: " + soundName);
        Entity e = new Entity();
        e.add(new SoundComponent(soundName));
        Engine.addEntity(e);
    }
    
}

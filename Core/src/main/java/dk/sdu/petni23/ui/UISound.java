package dk.sdu.petni23.ui;

import dk.sdu.petni23.soundnode.SoundManager;

public class UISound {
    private static final SoundManager soundManager = new SoundManager();

    public static void play(String soundName) {
        System.out.println("🔊 [UI] Playing sound: " + soundName);
        soundManager.playSound(soundName, 0); // delay in milliseconds
    }
}

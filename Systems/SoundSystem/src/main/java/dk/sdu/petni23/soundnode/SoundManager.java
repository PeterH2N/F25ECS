package dk.sdu.petni23.soundnode;


import dk.sdu.petni23.common.sound.SoundEffect;

import java.util.Map;
import java.util.concurrent.*;

public class SoundManager {
    private static final ExecutorService scheduler = Executors.newCachedThreadPool();
    //private static final Map<String, ScheduledFuture<?>> loopingSounds = new ConcurrentHashMap<>();

    public static void playSound(SoundEffect soundEffect, double volume) {
        scheduler.execute(() -> soundEffect.play(soundEffect.volume * (float)volume * 0.02f));
    }

    public static void playSound(SoundEffect soundEffect) {
        playSound(soundEffect, 1);
    }

}

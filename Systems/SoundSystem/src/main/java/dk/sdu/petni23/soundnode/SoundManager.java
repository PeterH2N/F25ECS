package dk.sdu.petni23.soundnode;


import dk.sdu.petni23.common.sound.SoundEffect;

import java.util.Map;
import java.util.concurrent.*;

public class SoundManager {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
    private static final Map<String, ScheduledFuture<?>> loopingSounds = new ConcurrentHashMap<>();

    public static void playSound(SoundEffect soundEffect, int delayMillis, double volume) {
        scheduler.schedule(() -> {
            soundEffect.play(soundEffect.volume * (float)volume * 0.02f);
        }, delayMillis, TimeUnit.MILLISECONDS);
    }

    public static void playSound(SoundEffect soundEffect, int delayMillis) {
        playSound(soundEffect, delayMillis, 1);
    }

    public void startLoopingSound(String action, int intervalMillis) {
        if (loopingSounds.containsKey(action)) {
            return;
        }

        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(() -> {

        }, 0, intervalMillis, TimeUnit.MILLISECONDS);

        loopingSounds.put(action, future);
    }

    public void stopLoopingSound(String action) {
        ScheduledFuture<?> future = loopingSounds.remove(action);
        if (future != null) {
            future.cancel(false);
        }
    }
}

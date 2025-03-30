package dk.sdu.petni23.soundnode;


import java.util.Map;
import java.util.concurrent.*;

public class SoundManager {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    private static final Map<String, SoundClip> soundCache = new ConcurrentHashMap<>();
    private static final Map<String, ScheduledFuture<?>> loopingSounds = new ConcurrentHashMap<>();

    public void playSound(String action, int delayMillis) {
    
        scheduler.schedule(() -> {
            SoundClip soundClip = soundCache.computeIfAbsent(action, SoundClip::new);
            soundClip.play(delayMillis);
        }, delayMillis, TimeUnit.MILLISECONDS);
    }

    public void preloadSounds(String... soundNames) {
        for (String name : soundNames) {
            soundCache.computeIfAbsent(name, SoundClip::new);
        }
    }

    public void playSound(String action, int delayMillis, double volume) {
        scheduler.schedule(() -> {
            SoundClip soundClip = soundCache.computeIfAbsent(action, SoundClip::new);
            int vol = (int)(volume * 10f);
            soundClip.setVolume((float) vol / 50);
            soundClip.play(delayMillis);
        }, delayMillis, TimeUnit.MILLISECONDS);
    }

    public void startLoopingSound(String action, int intervalMillis) {
        if (loopingSounds.containsKey(action)) {
            return;
        }

        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(() -> {
            SoundClip soundClip = soundCache.computeIfAbsent(action, SoundClip::new);
            soundClip.play(intervalMillis);
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

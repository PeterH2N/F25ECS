package dk.sdu.petni23.soundnode;


import java.util.Map;
import java.util.concurrent.*;
import dk.sdu.petni23.soundnode.SoundEntity;

public class SoundManager {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    private static final Map<String, SoundEntity> soundCache = new ConcurrentHashMap<>();
    private static final Map<String, ScheduledFuture<?>> loopingSounds = new ConcurrentHashMap<>();

    public void playSound(String action, int delayMillis) {
    
        scheduler.schedule(() -> {
            SoundEntity soundEntity = soundCache.computeIfAbsent(action, SoundEntity::new);
            soundEntity.play(delayMillis);
        }, delayMillis, TimeUnit.MILLISECONDS);
    }
    

    public void playSound(String action, int delayMillis, double volume) {
        scheduler.schedule(() -> {
            SoundEntity soundEntity = soundCache.computeIfAbsent(action, SoundEntity::new);
            int vol = (int)(volume * 10f);
            soundEntity.setVolume((float) vol / 50);
            soundEntity.play(delayMillis);
        }, delayMillis, TimeUnit.MILLISECONDS);
    }

    public void startLoopingSound(String action, int intervalMillis) {
        if (loopingSounds.containsKey(action)) {
            return;
        }

        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(() -> {
            SoundEntity soundEntity = soundCache.computeIfAbsent(action, SoundEntity::new);
            soundEntity.play(intervalMillis);
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

package dk.sdu.petni23.soundnode;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class SoundClip
{
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Random random = new Random();
    private final List<Clip> clips = new ArrayList<>();
    private float sfxVolume = 0.2f;

    public SoundClip(String action) {
        String soundFile = action + ".wav";
        System.out.println("🧪 Attempting to load: " + soundFile);
    
        URL soundUrl = getClass().getClassLoader().getResource(soundFile);
        if (soundUrl == null) {
            System.err.println("❌ Sound file not found: " + soundFile);
            return;
        }
    
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clips.add(clip);
            System.out.println("✅ Loaded sound: " + soundFile);
        } catch (Exception e) {
            System.err.println("❌ Error loading sound: " + soundFile);
            e.printStackTrace();
        }
    }
    
    public void play(int delayMillis) {
        System.out.println("▶️ Playing sound (delay: " + delayMillis + "ms), clips.size = " + clips.size());
        if (clips.isEmpty())
            return;
    
        Clip clipToPlay = clips.get(0);
        if (delayMillis > 0) {
            scheduler.schedule(() -> playClip(clipToPlay, sfxVolume), delayMillis, TimeUnit.MILLISECONDS);
        } else {
            playClip(clipToPlay, sfxVolume);
        }
    }
    

    private void playClip(Clip clip, float volume) {
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        setClipVolume(clip, volume);
        clip.start();
    }

    private void setClipVolume(Clip clip, float volume) {
        try {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (volume <= 0.01) ? gain.getMinimum() : (float)(20 * Math.log10(volume));
            gain.setValue(Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), dB)));
        } catch (Exception e) {
            System.err.println("❌ Error setting volume");
        }
    }

    public void setVolume(float newVolume) {
        sfxVolume = Math.max(0.0f, Math.min(1.0f, newVolume));
    }

    public float getVolume() {
        return sfxVolume;
    }

    public void stop() {
        for (Clip clip : clips) {
            if (clip != null) {
                clip.stop();
                clip.close();
            }
        }
    }
}

package dk.sdu.petni23.common.sound;
import java.io.*;
import java.net.URL;
import java.util.Objects;
import javax.sound.sampled.*;
import javax.sound.sampled.FloatControl;
public enum SoundEffect {
    BACKGROUND_MUSIC("background_music.wav"),
    CLICK("click1.wav"),
    COIN_BAG_DROP("coin_bag_drop1.wav", 0.8),
    COIN_PICKUP("coin_pickup1.wav", 0.5),
    DYNAMITE_THROW("dynamite_throw1.wav", 0.4),
    FOOTSTEP("footstep.wav"),
    FOOTSTEP_PLAYER("footstep_player.wav", 0.8),
    GOBLIN_HURT("goblin_hurt1.wav", 0.5),
    KNIGHT_HURT("knight_hurt1.wav", 0.5),
    MEAT_DROP("meat_drop1.wav", 0.8),
    MEAT_PICKUP("meat_pickup1.wav", 0.1),
    MINE_HIT("mine_hit1.wav", 0.5),
    SHEEP_BOUNCE("sheep_bounce1.wav"),
    SHEEP_HURT("sheep_hurt1.wav"),
    SHEEP_WALK("sheep_walk1.wav"),
    STONE_DROP("stone_drop1.wav", 0.8),
    STONE_PICKUP("stone_pickup1.wav", 0.5),
    SWORD_HIT("sword_hit.wav"),
    THROW_EXPLOSION("throw_explosion1.wav", 0.4),
    TREE_HIT("tree_hit1.wav", 0.5),
    WOOD_DROP("wood_drop1.wav", 0.8),
    WOOD_PICKUP("wood_pickup1.wav", 0.5),
    WOOSH1("woosh1.wav"),
    WOOSH2("woosh2.wav");

    public final float volume;

    private Clip clip;

    SoundEffect(String soundFileName, double volume) {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            URL url = Objects.requireNonNull(SoundEffect.class.getResource("/soundeffects/" + soundFileName));
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        this.volume = (float) volume;
    }

    SoundEffect(String soundFileName) {
        this(soundFileName, 1);
    }

    // Play or Re-play the sound effect from the beginning, by rewinding.
    public void play() {
        if (getVolume() < 0.0000001) return;
        if (clip.isRunning())
            clip.stop();   // Stop the player if it is still running
        clip.setFramePosition(0); // rewind to the beginning
        clip.start();     // Start playing
    }

    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    // Optional static method to preload all the sound files.
    public static void init() {
        values(); // calls the constructor for all the elements
    }
}

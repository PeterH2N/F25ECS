package dk.sdu.petni23.ui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UISound {
    private static final Map<String, Clip> clipCache = new HashMap<>();

    public static void attachToScene(Scene scene) {
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            Node node = (Node) event.getTarget();
            while (node != null && !(node instanceof Button)) {
                node = node.getParent();
            }
            if (node instanceof Button) {
                play("click1"); // or "ui_click" etc.
            }
        });
    }

    public static void play(String name) {
        System.out.println("🔊 [UI] Playing sound: " + name);
        try {
            Clip clip = clipCache.get(name);
            if (clip == null) {
                clip = loadClip(name);
                if (clip != null) {
                    clipCache.put(name, clip);
                } else {
                    System.err.println("❌ [UI] Failed to load sound: " + name);
                    return;
                }
            }

            // Restart and play
            clip.setFramePosition(0);
            clip.start();
        } catch (Exception e) {
            System.err.println("❌ [UI] Error playing sound '" + name + "':");
            e.printStackTrace();
        }
    }

    private static Clip loadClip(String name) {
        try {
            URL soundUrl = UISound.class.getResource("/" + name + ".wav");
            if (soundUrl == null) {
                System.err.println("❌ [UI] Sound file not found: " + name + ".wav");
                return null;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return null;
        }
    }
}
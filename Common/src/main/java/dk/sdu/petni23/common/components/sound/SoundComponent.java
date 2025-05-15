package dk.sdu.petni23.common.components.sound;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.sound.SoundEffect;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Component;

/**
 * IMPORTANT: Entities with sound components will be deleted after the sound is played.
 * */
public class SoundComponent extends Component {
    public final SoundEffect soundEffect;
    public final Vector2D position;
    public final long playAt; // delay in ms

    public SoundComponent(SoundEffect soundEffect, Vector2D position) {
        this(soundEffect, position, 0);
    }

    public SoundComponent(SoundEffect soundEffect, Vector2D position, int delayMillis) {
        this.soundEffect = soundEffect;
        this.position = new Vector2D(position);
        this.playAt = GameData.getCurrentMillis() + delayMillis;
    }
}

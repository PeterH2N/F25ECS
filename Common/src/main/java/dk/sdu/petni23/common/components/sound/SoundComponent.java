package dk.sdu.petni23.common.components.sound;

import dk.sdu.petni23.gameengine.Component;

public class SoundComponent extends Component {
    public String action;
    public boolean triggered = false;
    public long playAt = 0; // delay in ms
    public double volume = 1.0; // default full volume

    public SoundComponent(String action) {
        this(action, 0, 1.0);
    }

    public SoundComponent(String action, long delayMillis) {
        this(action, delayMillis, 1.0);
    }

    public SoundComponent(String action, long delayMillis, double volume) {
        this.action = action;
        this.triggered = true;
        this.playAt = System.currentTimeMillis() + delayMillis;
        this.volume = volume;
    }
}

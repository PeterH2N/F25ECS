package dk.sdu.petni23.common.components.sound;

import dk.sdu.petni23.gameengine.Component;

import java.util.Set;

public class FootstepSoundComponent extends Component {
    public String sound;
    public Set<Integer> triggerFrames;
    public int lastFrame = -1;

    public FootstepSoundComponent(String sound, Set<Integer> triggerFrames) {
        this.sound = sound;
        this.triggerFrames = triggerFrames;
    }

    // Optional: fallback constructor if you don't care about custom frames
    public FootstepSoundComponent(String sound) {
        this(sound, Set.of(1, 4)); // Default to frames 1 and 4
    }
}

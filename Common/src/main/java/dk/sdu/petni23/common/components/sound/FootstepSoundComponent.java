package dk.sdu.petni23.common.components.sound;

import dk.sdu.petni23.common.sound.SoundEffect;
import dk.sdu.petni23.gameengine.Component;

import java.util.Set;

public class FootstepSoundComponent extends Component {
    public final SoundEffect soundEffect;
    public Set<Integer> triggerFrames;
    public int lastFrame = -1;

    public FootstepSoundComponent(SoundEffect soundEffect, Set<Integer> triggerFrames) {
        this.soundEffect = soundEffect;
        this.triggerFrames = triggerFrames;
    }
}

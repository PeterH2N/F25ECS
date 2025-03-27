package dk.sdu.petni23.common.components.sound;

import dk.sdu.petni23.gameengine.Component;

public class FootstepSoundComponent extends Component {
    public String sound;
    public int lastFrame = -1;
    public FootstepSoundComponent(String sound) {
        this.sound = sound;
    }
}

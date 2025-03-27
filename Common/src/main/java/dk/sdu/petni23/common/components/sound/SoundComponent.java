package dk.sdu.petni23.common.components.sound;

import dk.sdu.petni23.gameengine.Component;

public class SoundComponent extends Component {
    public String action;
    public boolean triggered = false; // Set true when an action should play a sound

    public SoundComponent(String action) {
        this.action = action;
        this.triggered = true;
    }
    
}

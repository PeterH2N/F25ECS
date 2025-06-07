package dk.sdu.petni23.common.components;

import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Component;

public class RespawnComponent extends Component {
    public boolean active = false;
    public float countdown = -1;
    public boolean alreadyDead = false;
    public boolean wasRemoved = false; 
    public Vector2D spawnPosition;
}

package dk.sdu.petni23.common.components.gameflow;

import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Component;

public class SpawnComponent extends Component {
    
    public boolean spawnPoint;
    public Vector2D pos;
    public boolean sustainableAfterRoundEnd;
    public SpawnComponent(boolean spawnPoint, Vector2D pos, boolean sustainableAfterRoundEnd){
        this.spawnPoint = spawnPoint;
        this.pos = pos;
        this.sustainableAfterRoundEnd = sustainableAfterRoundEnd;
    }
}

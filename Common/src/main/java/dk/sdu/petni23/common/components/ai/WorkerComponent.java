package dk.sdu.petni23.common.components.ai;

import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.Entity;

public class WorkerComponent extends Component {
    public final Vector2D home = new Vector2D();

    public State state = State.COLLECTING;

    public enum State {
        COLLECTING,
        RETURNING
    }
}

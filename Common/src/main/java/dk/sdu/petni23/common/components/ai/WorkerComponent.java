package dk.sdu.petni23.common.components.ai;

import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class WorkerComponent extends Component {
    public transient Entity home;

    public State state = State.COLLECTING;

    public enum State {
        COLLECTING,
        RETURNING
    }
}

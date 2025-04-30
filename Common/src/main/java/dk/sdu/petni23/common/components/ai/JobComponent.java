package dk.sdu.petni23.common.components.ai;

import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;

public class JobComponent extends Component {
    public enum State {
        GO_TO_MINE,
        MINING,
        GO_TO_PLAYER,
        DELIVER,
        IDLE
    }

    public static final IEntitySPI.Type STONE_TYPE = IEntitySPI.Type.STONE; // skift til korrekt type

    public State currentState = State.GO_TO_MINE;
    public Vector2D targetPosition = null;
    public int miningProgress = 0;
}

package dk.sdu.petni23.common.components.actions;

import dk.sdu.petni23.gameengine.Component;

import java.util.ArrayList;
import java.util.List;

public class ActionSetComponent extends Component {
    public transient List<Action> actions = new ArrayList<>();
    public long lastActionTime = 0;
    public Action lastAction = Action.DEFAULT;
    public boolean hasDispatched = false;
}

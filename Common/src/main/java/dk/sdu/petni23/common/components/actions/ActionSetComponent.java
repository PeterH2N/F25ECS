package dk.sdu.petni23.common.components.actions;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.gameengine.Component;

import java.util.ArrayList;
import java.util.List;

public class ActionSetComponent extends Component
{
    public List<Action> actions = new ArrayList<>();
    public long lastActionTime = 0;
    public Action lastAction = null;
    public boolean hasDispatched = false;

    public void performAction(int i) {
        if (isPerformingAction()) return;

        long now = GameData.getCurrentMillis();
        lastAction = actions.get(i);
        lastActionTime = now;
    }

    public boolean isPerformingAction() {
        long now = GameData.getCurrentMillis();
        if (lastAction == null) return false;
        return now < lastActionTime + lastAction.duration;
    }
}

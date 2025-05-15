package dk.sdu.petni23.common.components.ai;

import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.Entity;

public class PathFindingComponent extends Component {
    public transient Path path = new Path();
    public transient Entity opp = null;
    public boolean keepPath = false;
}

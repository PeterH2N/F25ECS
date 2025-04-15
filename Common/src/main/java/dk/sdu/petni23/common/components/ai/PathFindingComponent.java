package dk.sdu.petni23.common.components.ai;

import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.Entity;

public class PathFindingComponent extends Component {
    public Path path = new Path();
    public Entity opp = null;
    public boolean keepPath = false;
}

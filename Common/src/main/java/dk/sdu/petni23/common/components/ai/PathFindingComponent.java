package dk.sdu.petni23.common.components.ai;

import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PathFindingComponent extends Component {
    public final Path path = new Path();
    public final List<Vector2D> collisionCells = new ArrayList<>();
}

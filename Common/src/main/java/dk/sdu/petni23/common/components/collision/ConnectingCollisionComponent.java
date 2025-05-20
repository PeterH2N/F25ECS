package dk.sdu.petni23.common.components.collision;

import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;

import java.util.HashMap;
import java.util.Map;

public class ConnectingCollisionComponent extends Component {

    public final IEntitySPI.Type type;

    public ConnectingCollisionComponent(IEntitySPI.Type type) {
        this.type = type;
    }

    public static Map<IEntitySPI.Type, Double> insets = new HashMap<>();
    static {
        insets.put(IEntitySPI.Type.STONE_WALL, 0.05);
        insets.put(IEntitySPI.Type.WOODEN_FENCE, 0.8);
    }
}

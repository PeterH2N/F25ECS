package dk.sdu.petni23.common.components.rendering;

import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;

public class ConnectingSpriteComponent extends Component {

    public final IEntitySPI.Type type;

    public ConnectingSpriteComponent(IEntitySPI.Type type) {
        this.type = type;
    }
}

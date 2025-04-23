package dk.sdu.petni23.common.components.rendering;

import dk.sdu.petni23.gameengine.Component;

public class ConnectingSpriteComponent extends Component {

    public final Type type;

    public ConnectingSpriteComponent(Type type) {
        this.type = type;
    }
    public enum Type {
        NONE,
        STONE_WALL,
    }
}

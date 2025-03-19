package dk.sdu.petni23.common.components.hp;

import dk.sdu.petni23.gameengine.Component;

public class LayerComponent extends Component
{
    public final Layer layer;

    public LayerComponent(Layer layer)
    {
        this.layer = layer;
    }

    public enum Layer {
        PLAYER(0b01),
        ENEMY(0b10),
        ALL(0),
        NONE(Integer.MAX_VALUE);

        private final int layer;

        Layer(int l) {
            layer = l;
        }

        public int value() {
            return layer;
        }
    }
}

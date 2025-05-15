package dk.sdu.petni23.common.components.damage;

import dk.sdu.petni23.gameengine.Component;

public class LayerComponent extends Component
{
    public final Layer layer;

    public LayerComponent(Layer layer)
    {
        this.layer = layer;
    }

    public enum Layer {
        PLAYER(0b0001, 0b10),
        ENEMY(0b0010, PLAYER.layer),
        NPC(0b0100, PLAYER.layer),
        NPC_TARGET(PLAYER.layer, NPC.layer),
        ALL(0, Integer.MAX_VALUE),
        NONE(Integer.MAX_VALUE, Integer.MAX_VALUE);

        private final int layer;
        private final int opponent;

        Layer(int l, int opponent) {
            layer = l;
            this.opponent = opponent;
        }

        public int value() {
            return layer;
        }
        public int opponent() {
            return opponent;
        }
    }
}

package dk.sdu.petni23.common.components.rendering;

import dk.sdu.petni23.gameengine.Component;

public class DisplayComponent extends Component
{
    public final Layer order;
    public DisplayComponent(Layer layer) {
        this.order = layer;
    }
    public enum Layer
    {
        BACKGROUND(0),
        FOREGROUND(2),
        EFFECT(3);
        private final int i;
        Layer(int i) {
            this.i = i;
        }
        public int value() {
            return i;
        }
    }
}

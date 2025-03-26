package dk.sdu.petni23.common.components;

import dk.sdu.petni23.gameengine.Component;

public class DisplayComponent extends Component
{
    public final Order order;
    public DisplayComponent(Order order) {
        this.order = order;
    }
    public enum Order {
        BACKGROUND(0),
        MAP(1),
        FOREGROUND(2);
        private final int i;
        Order(int i) {
            this.i = i;
        }
        public int value() {
            return i;
        }
    }
}

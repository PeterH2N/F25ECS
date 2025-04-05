package dk.sdu.petni23.common.components.health;

import dk.sdu.petni23.gameengine.Component;
import javafx.scene.paint.Color;

public class HealthBarComponent extends Component {

    public int width;
    public int height;
    public Color color;

    public HealthBarComponent(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }
}

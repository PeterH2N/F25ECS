package dk.sdu.petni23.common.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class GameData
{
    private static final IntegerProperty displayWidth = new SimpleIntegerProperty(0);
    private static final IntegerProperty displayHeight = new SimpleIntegerProperty(0);
    private static final double viewPortWidth = 40;
    private static final DoubleProperty ppmProperty = new SimpleDoubleProperty();
    public static final double gravitationalConstant = 6.6743e-11;
    public static final Pane gameWindow = new Pane();

    static {
        ppmProperty.bind(displayWidth.divide(viewPortWidth));
    }
    public static IntegerProperty displayWidthProperty() {
        return displayWidth;
    }
    public static IntegerProperty displayHeightProperty() {
        return displayHeight;
    }
    public static int getDisplayWidth()
    {
        return displayWidth.get();
    }

    public static int getDisplayHeight()
    {
        return displayHeight.get();
    }
    public static double getPPM() {
        return ppmProperty.get();
    }
}

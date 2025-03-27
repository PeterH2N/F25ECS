package dk.sdu.petni23.common;

import dk.sdu.petni23.common.misc.GameKeys;
import dk.sdu.petni23.common.misc.Viewport;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.common.world.GameWorld;
import dk.sdu.petni23.common.world.Tile;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameData
{
    private static final DoubleProperty displayWidth = new SimpleDoubleProperty(800);
    private static final DoubleProperty displayHeight = new SimpleDoubleProperty(600);
    private static final DoubleProperty ppmProperty = new SimpleDoubleProperty();
    public static final int worldSize = 76;
    private static final DoubleProperty displayRatioProperty = new SimpleDoubleProperty();
    private static final DoubleProperty tileRatioProperty = new SimpleDoubleProperty();
    public static final Pane gameWindow = new Pane();
    public static final Scene scene = new Scene(gameWindow);
    public static final GameKeys gameKeys = new GameKeys();
    private static long currentTime = java.lang.System.nanoTime();
    private static double deltaTime = 0;
    private static long frameTime = 0;
    private static long currentMillis = 0;
    public static final Viewport camera = new Viewport(Math.min(40, worldSize));
    public static final GameWorld world = new GameWorld();

    static {
        ppmProperty.bind(displayWidth.divide(camera.widthProperty));
        displayRatioProperty.bind(displayHeight.divide(displayWidth));
        tileRatioProperty.bind(ppmProperty.multiply(1.0 / 64.0)); // reciprocal of 64 (tilesize in pixels) multiplied by pixels per tile
        displayWidth.bind(gameWindow.widthProperty());
        displayHeight.bind(gameWindow.heightProperty());
    }
    public static DoubleProperty displayWidthProperty() {
        return displayWidth;
    }
    public static DoubleProperty displayHeightProperty() {
        return displayHeight;
    }
    public static int getDisplayWidth()
    {
        return (int)displayWidth.get();
    }

    public static int getDisplayHeight()
    {
        return (int)displayHeight.get();
    }

    public static double getDisplayRatio() {
        return displayRatioProperty.get();
    }

    public static DoubleProperty getDisplayRatioProperty() {
        return displayRatioProperty;
    }
    public static double getPPM() {
        return ppmProperty.get();
    }

    public static double getTileRatio() {
        return tileRatioProperty.get();
    }

    public static void setTime(long now) {
        long prevTime = currentTime;
        currentTime = now;
        deltaTime = (double)(currentTime - prevTime) / 1000000000;
        currentMillis = now / 1000000;
    }
    public static long getFrameTime()
    {
        return frameTime;
    }

    public static void setFrameTime(long frameTime)
    {
        GameData.frameTime = frameTime;
    }

    public static long getCurrentMillis() {
        return currentMillis;
    }

    public static double getDeltaTime()
    {
        return deltaTime;
    }

    public static Vector2D toScreenSpace(double x, double y) {
        Vector2D origin = new Vector2D((double) GameData.getDisplayWidth() / 2, (double) GameData.getDisplayHeight() / 2);
        Vector2D p = new Vector2D(x, y).getSubtracted(camera.getCenter()); // distance from camera center to point

        return origin.getAdded(new Vector2D(p.x * getPPM(), -p.y * getPPM()));
    }

    public static Vector2D toScreenSpace(Vector2D v) {
        return toScreenSpace(v.x, v.y);
    }

    public static Vector2D toWorldSpace(double x, double y) {
        // (0,0) in screen space
        Vector2D origin = new Vector2D((double) getDisplayWidth() / 2, (double) getDisplayHeight() / 2);

        double wx = (x - origin.x) / getPPM() + camera.getCenter().x;
        double wy = -(y - origin.y) / getPPM() + camera.getCenter().y;

        return new Vector2D(wx, wy);
    }

    public static Vector2D toWorldSpace(Vector2D v) {
        return toWorldSpace(v.x, v.y);
    }

    /**
     * Returns null if not on solid ground
    * */
    public static Vector2D randomWorldPos() {
        double x = Math.random() * GameData.worldSize - (double) GameData.worldSize / 2;
        double y = Math.random() * GameData.worldSize - (double) GameData.worldSize / 2;
        if (world.map.getTile((int) x, (int) y).type != Tile.Type.GRASS) {
            return null;
        }
        else return new Vector2D(x, y);
    }
}

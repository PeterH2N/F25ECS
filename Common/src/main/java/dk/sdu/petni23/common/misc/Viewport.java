package dk.sdu.petni23.common.misc;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.PositionComponent;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Viewport
{
    public PositionComponent following = new PositionComponent();
    public Vector2D center = new Vector2D(0,0);
    public final DoubleProperty widthProperty = new SimpleDoubleProperty();
    public final DoubleProperty heightProperty = new SimpleDoubleProperty();

    public Viewport(double width)
    {
        widthProperty.set(width);
        heightProperty.bind(widthProperty.multiply(GameData.getDisplayRationProperty()));
    }

    public double getWidth()
    {
        return widthProperty.get();
    }

    public double getHeight() {
        return heightProperty.get();
    }

    public void setWidth(double width)
    {
        widthProperty.set(width);
    }

    public Vector2D getCenter()
    {
        return center;
    }

    public void setCenter(Vector2D center)
    {
        this.center.set(center);
    }

    public void setCenter(double x, double y) {
        this.center.set(x,y);
    }
}

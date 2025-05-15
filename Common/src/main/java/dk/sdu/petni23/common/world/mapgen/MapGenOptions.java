package dk.sdu.petni23.common.world.mapgen;

import dk.sdu.petni23.common.GameData;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class MapGenOptions
{
    public DoubleProperty islandRadius = new SimpleDoubleProperty(88);
    public DoubleProperty islandShapeAmplitude = new SimpleDoubleProperty(30);
    public DoubleProperty islandShapeFrequency = new SimpleDoubleProperty(0.0545);
    public DoubleProperty coastAmplitude = new SimpleDoubleProperty(5);
    public DoubleProperty coastFrequency = new SimpleDoubleProperty(0.302);
    public IntegerProperty shapeOffset = new SimpleIntegerProperty(2075);
    public IntegerProperty coastOffset = new SimpleIntegerProperty(984);

    public DoubleProperty sandThreshold = new SimpleDoubleProperty(0.24);
    public DoubleProperty grassThreshold = new SimpleDoubleProperty(0.30);
    public DoubleProperty landFrequency = new SimpleDoubleProperty(0.03);
    public IntegerProperty landOffset = new SimpleIntegerProperty(511);

    public DoubleProperty forestThreshold = new SimpleDoubleProperty(0.65);
    public DoubleProperty forestFrequency = new SimpleDoubleProperty(0.02);
    public IntegerProperty forestOffset = new SimpleIntegerProperty(0);
    public DoubleProperty forestDensity = new SimpleDoubleProperty(0.3);
}

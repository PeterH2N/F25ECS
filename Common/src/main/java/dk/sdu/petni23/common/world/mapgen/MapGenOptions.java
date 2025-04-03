package dk.sdu.petni23.common.world.mapgen;

import dk.sdu.petni23.common.GameData;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MapGenOptions
{
    public DoubleProperty islandRadius = new SimpleDoubleProperty(88);
    public DoubleProperty islandShapeAmplitude = new SimpleDoubleProperty(30);
    public DoubleProperty islandShapeFrequency = new SimpleDoubleProperty(0.0545);
    public DoubleProperty coastAmplitude = new SimpleDoubleProperty(5);
    public DoubleProperty coastFrequency = new SimpleDoubleProperty(0.302);
    public DoubleProperty sandThreshold = new SimpleDoubleProperty(0.28);
    public DoubleProperty grassThreshold = new SimpleDoubleProperty(0.34);
    public DoubleProperty landFrequency = new SimpleDoubleProperty(0.03);
}

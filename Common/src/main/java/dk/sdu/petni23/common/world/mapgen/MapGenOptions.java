package dk.sdu.petni23.common.world.mapgen;

import dk.sdu.petni23.common.GameData;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MapGenOptions
{
    public DoubleProperty maxIslandRadius = new SimpleDoubleProperty((double) GameData.worldSize / 2 - 2);
    public DoubleProperty minIslandRadius = new SimpleDoubleProperty((double) GameData.worldSize / 2 - 10);
    public DoubleProperty coastRuggedness = new SimpleDoubleProperty(0.045);
    public DoubleProperty sandThreshold = new SimpleDoubleProperty(0.28);
    public DoubleProperty grassThreshold = new SimpleDoubleProperty(0.34);
    public DoubleProperty landFrequency = new SimpleDoubleProperty(0.03);
}

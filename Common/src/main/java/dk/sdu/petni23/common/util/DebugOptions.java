package dk.sdu.petni23.common.util;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class DebugOptions {
    public boolean active = false;
    public BooleanProperty showHitBoxes = new SimpleBooleanProperty(true);
    public BooleanProperty showColliders = new SimpleBooleanProperty(true);
    public BooleanProperty showHP = new SimpleBooleanProperty(true);
    public BooleanProperty showGrid = new SimpleBooleanProperty(true);
}

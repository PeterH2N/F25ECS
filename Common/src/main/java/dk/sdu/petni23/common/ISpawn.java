package dk.sdu.petni23.common;


import java.util.ArrayList;

import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;

public interface ISpawn {
    public void start(Vector2D pos);
    public void stop();
}

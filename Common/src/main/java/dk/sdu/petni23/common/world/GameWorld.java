package dk.sdu.petni23.common.world;

import dk.sdu.petni23.common.misc.Manifold;

import java.util.ArrayList;
import java.util.List;

public class GameWorld
{
    public List<Manifold> collisionManifolds = new ArrayList<>();
    public List<Manifold> hitBoxManifolds = new ArrayList<>();

    public GameMap map = new GameMap();

}

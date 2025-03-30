package dk.sdu.petni23.common.world;

import dk.sdu.petni23.gameengine.util.Collider;
import dk.sdu.petni23.common.misc.Manifold;
import dk.sdu.petni23.gameengine.node.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameWorld
{
    public final List<Manifold> collisionManifolds = new ArrayList<>();
    public final List<Manifold> hitBoxManifolds = new ArrayList<>();

    public GameMap map = new GameMap();

}

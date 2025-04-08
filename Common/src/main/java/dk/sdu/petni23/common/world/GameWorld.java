package dk.sdu.petni23.common.world;

import dk.sdu.petni23.common.util.Collider;
import dk.sdu.petni23.common.misc.Manifold;
import dk.sdu.petni23.common.util.ColliderPair;
import dk.sdu.petni23.common.world.mapgen.GameMap;
import dk.sdu.petni23.common.world.mapgen.MapGenOptions;
import dk.sdu.petni23.gameengine.node.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameWorld
{
    public final List<Manifold> collisionManifolds = new ArrayList<>();
    public final List<Manifold> hitBoxManifolds = new ArrayList<>();
    public final Map<Node, Collider> collisionColliders = new HashMap<>();
    public final Map<Node, Collider> hitBoxColliders = new HashMap<>();

    public final Map<ColliderPair, Boolean> collisionColliderPairs = new HashMap<>();
    public final Map<ColliderPair, Boolean> hitBoxColliderPairs = new HashMap<>();

    public MapGenOptions mapGenOptions = new MapGenOptions();

    public final GameMap map;

    public GameWorld(MapGenOptions options)
    {
        this.map = new GameMap(options);
    }
}

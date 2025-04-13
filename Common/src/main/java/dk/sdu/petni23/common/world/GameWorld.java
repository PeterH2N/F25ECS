package dk.sdu.petni23.common.world;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.Collider;
import dk.sdu.petni23.common.misc.Manifold;
import dk.sdu.petni23.common.util.ColliderPair;
import dk.sdu.petni23.common.util.Vector2D;
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

    public static final List<Collider>[][] collisionGrid = (ArrayList<Collider>[][])new ArrayList[(int) GameData.worldSize][(int) GameData.worldSize];
    public static final List<Collider>[][] hitBoxGrid = (ArrayList<Collider>[][])new ArrayList[(int) GameData.worldSize][(int) GameData.worldSize];

    public MapGenOptions mapGenOptions = new MapGenOptions();

    public final GameMap map;

    public GameWorld(MapGenOptions options)
    {
        this.map = new GameMap(options);
    }

    public static Vector2D toTileSpace(double x, double y) {
        int X = (int) Math.floor(x + GameData.worldSize * 0.5);
        int Y = (int) Math.floor(-y + GameData.worldSize * 0.5);

        if (X < 0) X = 0;
        if (Y < 0) Y = 0;
        if (X >= GameData.worldSize - 1) X = GameData.worldSize - 1;
        if (Y >= GameData.worldSize - 1) Y = GameData.worldSize - 1;

        return new Vector2D(X, Y);
    }

    public static Vector2D toTileSpace(Vector2D v) {
        return toTileSpace(v.x, v.y);
    }



    public static Vector2D toWorldSpace(int x, int y) {
        int X = (int) (x - GameData.worldSize * 0.5);
        int Y = (int) -(y - GameData.worldSize * 0.5);

        return new Vector2D(X, Y);
    }
    public static Vector2D toWorldSpace(Vector2D v) {
        return toWorldSpace((int) v.x, (int) v.y);
    }
}

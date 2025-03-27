package dk.sdu.petni23.collisionnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HasShapeComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.misc.Manifold;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;

import java.util.*;

public class BroadPhaseSystem implements ISystem, IPluginService
{
    private static final List<Collider>[][] collisionGrid = (ArrayList<Collider>[][])new ArrayList[(int) GameData.worldSize][(int) GameData.worldSize];
    private static final List<Collider>[][] hitBoxGrid = (ArrayList<Collider>[][])new ArrayList[(int) GameData.worldSize][(int) GameData.worldSize];
    private final static Map<Node, Collider> collisionColliders = new HashMap<>();
    private final static Map<Node, Collider> hitBoxColliders = new HashMap<>();
    @Override
    public void update(double deltaTime)
    {
        clearGrid(collisionGrid);
        clearGrid(hitBoxGrid);
        populateGrid(CollisionNode.class, collisionGrid, CollisionComponent.class, collisionColliders);
        populateGrid(HitBoxNode.class, hitBoxGrid, HitBoxComponent.class, hitBoxColliders);
        GameData.world.collisionManifolds.clear();
        GameData.world.hitBoxManifolds.clear();
        populateManifoldList(collisionGrid, GameData.world.collisionManifolds, collisionColliders);
        populateManifoldList(hitBoxGrid, GameData.world.hitBoxManifolds, hitBoxColliders);
    }

    @Override
    public int getPriority()
    {
        return Priority.POSTPROCESSING.get();
    }

    private void clearGrid(List<Collider>[][] grid) {
        for (int i = 0; i < collisionGrid.length; i++) {
            for (int j = 0; j < collisionGrid[i].length; j++) {
                grid[i][j].clear();
            }
        }
    }

    private void addNodeToGrid(Node node, List<Collider>[][] grid, Class<? extends HasShapeComponent> c, Map<Node, Collider> colliderMap) {
        PositionComponent positionComponent = node.getComponent(PositionComponent.class);
        if (positionComponent == null) return;
        Collider collider = colliderMap.get(node);
        if (collider == null) {
            collider = new Collider(node);
            colliderMap.put(node, collider);
        }
        if (!(node.getComponent(VelocityComponent.class) == null && !collider.cells.isEmpty())) {
            // we update collider cells
            collider.cells.clear();
            Vector2D pos = positionComponent.position.getAdded(node.getComponent(HasShapeComponent.class).offset);
            Shape shape = node.getComponent(c).getShape();
            Vector2D hbb = shape.getBB().getMultiplied(0.5);
            Vector2D min = pos.getSubtracted(hbb);
            Vector2D max = pos.getAdded(hbb);

            int startX = (int) (min.x + GameData.worldSize / 2);
            int startY = (int) (min.y + GameData.worldSize / 2);
            int endX = (int) (max.x + GameData.worldSize / 2);
            int endY = (int) (max.y + GameData.worldSize / 2);
            if (endX == GameData.worldSize) endX--;
            if (endY == GameData.worldSize) endY--;

            for (int x = startX; x <= endX; x++) {
                for (int y = startY; y <= endY; y++) {
                    Vector2D cell = new Vector2D(x, y);
                    collider.cells.add(cell);
                }
            }
        }
        for (var cell : collider.cells) {
            grid[(int) cell.y][(int) cell.x].add(collider);
        }
    }

    private void populateGrid(Class<? extends Node> c, List<Collider>[][] grid, Class<? extends HasShapeComponent> cc, Map<Node, Collider> colliderMap) {
        for (Node node : Engine.getNodes(c)) {
            addNodeToGrid(node, grid, cc, colliderMap);
        }
    }

    private void populateManifoldList(List<Collider>[][] grid, List<Manifold> manifoldList,Map<Node, Collider> colliderMap) {
        for (var collider1 : colliderMap.values()) {
            for (var cell : collider1.cells) {
                for (Collider collider2 : grid[(int) cell.y][(int) cell.x]) {
                    if (collider1 == collider2) continue;
                    var m = new Manifold(collider1.node, collider2.node);
                    if (!manifoldList.contains(m)) manifoldList.add(m);
                }
            }
        }
        /*for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                // for each collider in this tile
                for (Collider n1 : grid[y][x]) {
                    // loop through all neighboring tiles
                    int startX = (x == 0) ? 0 : x-1;
                    int startY = (y == 0) ? 0 : y-1;
                    int endX = (x == grid.length - 1) ? x : x+1;
                    int endY = (y == grid[x].length - 1) ? y : y+1;

                    for (int i = startX; i <= endX; i++) {
                        for (int j = startY; j <= endY; j++) {
                            // loop through all entities in this tile
                            for (Node n2 : grid[j][i]) {
                                if (n1 == n2)
                                    continue;

                                Manifold c = new Manifold(n1, n2);
                                if (!manifoldList.contains(c)) manifoldList.add(c);
                            }
                        }
                    }
                }
            }
        }*/
    }

    @Override
    public void start()
    {
        // initialize collision grid
        for (int i = 0; i < collisionGrid.length; i++) {
            for (int j = 0; j < collisionGrid[i].length; j++) {
                collisionGrid[i][j] = new ArrayList<>();
                hitBoxGrid[i][j] = new ArrayList<>();
            }
        }
    }

    @Override
    public void stop()
    {
        clearGrid(collisionGrid);
        clearGrid(hitBoxGrid);
    }

    static class Collider {
        List<Vector2D> cells = new ArrayList<>();
        final Node node;

        Collider(Node node)
        {
            this.node = node;
        }
    }
}

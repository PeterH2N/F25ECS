package dk.sdu.petni23.collisionnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.misc.Manifold;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;

import java.util.ArrayList;
import java.util.List;

public class BroadPhaseSystem implements ISystem, IPluginService
{
    private static final List<Node>[][] collisionGrid = (ArrayList<Node>[][])new ArrayList[(int) GameData.worldSize][(int) GameData.worldSize];
    private static final List<Node>[][] hitBoxGrid = (ArrayList<Node>[][])new ArrayList[(int) GameData.worldSize][(int) GameData.worldSize];
    @Override
    public void update(double deltaTime)
    {
        clearGrid(collisionGrid);
        clearGrid(hitBoxGrid);
        populateGrid(CollisionNode.class, collisionGrid);
        populateGrid(HitBoxNode.class, hitBoxGrid);
        GameData.world.collisionManifolds.clear();
        GameData.world.hitBoxManifolds.clear();
        populateManifoldList(collisionGrid, GameData.world.collisionManifolds);
        populateManifoldList(hitBoxGrid, GameData.world.hitBoxManifolds);
    }

    @Override
    public int getPriority()
    {
        return Priority.POSTPROCESSING.get();
    }

    private void clearGrid(List<Node>[][] grid) {
        for (int i = 0; i < collisionGrid.length; i++) {
            for (int j = 0; j < collisionGrid[i].length; j++) {
                grid[i][j].clear();
            }
        }
    }

    private void addNodeToGrid(Node node, List<Node>[][] grid) {
        PositionComponent positionComponent = node.getComponent(PositionComponent.class);
        if (positionComponent == null) return;


        int x = (int) (positionComponent.position.x + GameData.worldSize / 2);
        int y = (int) (positionComponent.position.y + GameData.worldSize / 2);
        if (x == GameData.worldSize) x--;
        if (y == GameData.worldSize) y--;

        grid[y][x].add(node);
    }

    private void populateGrid(Class<? extends Node> c, List<Node>[][] grid) {
        for (Node node : Engine.getNodes(c)) {
            addNodeToGrid(node, grid);
        }
    }

    private void populateManifoldList(List<Node>[][] grid, List<Manifold> manifoldList) {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                // for each entity in this tile
                for (Node n1 : grid[y][x]) {
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
        }
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

    }
}

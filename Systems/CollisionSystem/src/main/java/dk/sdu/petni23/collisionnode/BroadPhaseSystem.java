package dk.sdu.petni23.collisionnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.ControlComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HasShapeComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.DamageComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.util.Collider;
import dk.sdu.petni23.common.misc.Manifold;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.common.world.GameWorld;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;

import java.util.*;

import static dk.sdu.petni23.common.world.GameWorld.collisionGrid;
import static dk.sdu.petni23.common.world.GameWorld.hitBoxGrid;

public class BroadPhaseSystem implements ISystem, IPluginService
{
    @Override
    public void update(double deltaTime)
    {
        clearGrid(collisionGrid);
        clearGrid(hitBoxGrid);
        populateGrid(CollisionNode.class, collisionGrid, CollisionComponent.class, GameData.world.collisionColliders);
        populateGrid(HitBoxNode.class, hitBoxGrid, HitBoxComponent.class, GameData.world.hitBoxColliders);
        GameData.world.collisionManifolds.clear();
        GameData.world.hitBoxManifolds.clear();
        populateManifoldList(collisionGrid, GameData.world.collisionManifolds, GameData.world.collisionColliders);
        populateManifoldList(hitBoxGrid, GameData.world.hitBoxManifolds, GameData.world.hitBoxColliders);
    }

    @Override
    public int getPriority()
    {
        return Priority.POSTPROCESSING.get();
    }

    private static void clearGrid(List<Collider>[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].clear();
            }
        }
    }

    private static void addNodeToGrid(Node node, List<Collider>[][] grid, Class<? extends HasShapeComponent> c, Map<Node, Collider> colliderMap) {
        PositionComponent positionComponent = node.getComponent(PositionComponent.class);
        if (positionComponent == null) return;
        Collider collider = colliderMap.get(node);
        if (collider == null) {
            collider = new Collider(node);
            colliderMap.put(node, collider);
        }
        if ((node.getComponent(VelocityComponent.class) != null) || (node.getComponent(VelocityComponent.class) == null && collider.cells.isEmpty())) {
            // we update collider cells
            collider.cells.clear();
            Vector2D pos = positionComponent.position.getAdded(node.getComponent(HasShapeComponent.class).offset);
            Shape shape = node.getComponent(c).getShape();
            double hw = shape.aabb.hw;
            double hh = shape.aabb.hh;
            if (node.getComponent(VelocityComponent.class) != null) {
                hw += 0.05;
                hh += 0.05;
            }
            Vector2D min = pos.getSubtracted(hw, hh);
            Vector2D max = pos.getAdded(hw, hh);
            min.y += 0.01;
            max.x -= 0.01;

            var start = GameWorld.toTileSpace(min);
            var end = GameWorld.toTileSpace(max);

            for (int x = (int) start.x; x <= (int) end.x; x++) {
                for (int y = (int) start.y; y >= (int) end.y; y--) {
                    Vector2D cell = new Vector2D(x, y);
                    collider.cells.add(cell);
                }
            }
        }
        for (var cell : collider.cells) {
            grid[(int) cell.y][(int) cell.x].add(collider);
        }
    }

    static void populateGrid(Class<? extends Node> c, List<Collider>[][] grid, Class<? extends HasShapeComponent> cc, Map<Node, Collider> colliderMap) {
        for (Node node : Engine.getNodes(c)) {
            addNodeToGrid(node, grid, cc, colliderMap);
        }
    }

    private static void populateManifoldList(List<Collider>[][] grid, List<Manifold> manifoldList,Map<Node, Collider> colliderMap) {
        for (var collider1 : colliderMap.values()) {
            if (grid == collisionGrid && Engine.getEntity(collider1.node.getEntityID()).get(VelocityComponent.class) == null) continue;
            if (grid == hitBoxGrid && Engine.getEntity(collider1.node.getEntityID()).get(DamageComponent.class) == null) continue;
            for (var cell : collider1.cells) {
                for (Collider collider2 : grid[(int) cell.y][(int) cell.x]) {
                    if (collider1 == collider2) continue;
                    var m = new Manifold(collider1.node, collider2.node);
                    if (!manifoldList.contains(m)) manifoldList.add(m);
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
        clearGrid(collisionGrid);
        clearGrid(hitBoxGrid);
    }
}

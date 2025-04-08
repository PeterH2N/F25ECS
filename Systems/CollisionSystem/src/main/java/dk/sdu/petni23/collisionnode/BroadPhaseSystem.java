package dk.sdu.petni23.collisionnode;

import dk.sdu.petni23.common.GameData;
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
import dk.sdu.petni23.common.util.ColliderPair;
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

    @Override
    public void update(double deltaTime)
    {
        GameData.world.collisionColliders.keySet().removeIf(node -> Engine.getEntity(node.getEntityID()) == null);
        GameData.world.hitBoxColliders.keySet().removeIf(node -> Engine.getEntity(node.getEntityID()) == null);
        GameData.world.collisionColliderPairs.keySet().removeIf(pair -> Engine.getEntity(pair.c1().node.getEntityID()) == null || Engine.getEntity(pair.c2().node.getEntityID()) == null);
        GameData.world.hitBoxColliderPairs.keySet().removeIf(pair -> Engine.getEntity(pair.c1().node.getEntityID()) == null || Engine.getEntity(pair.c2().node.getEntityID()) == null);
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
            Vector2D min = pos.getSubtracted(shape.aabb.hw, shape.aabb.hh);
            Vector2D max = pos.getAdded(shape.aabb.hw, shape.aabb.hh);

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
                    ColliderPair cp = new ColliderPair(collider1, collider2);
                    if (grid == collisionGrid) {
                        if (!GameData.world.collisionColliderPairs.containsKey(cp)) {
                            if (Engine.getEntity(collider1.node.getEntityID()).get(VelocityComponent.class) == null && Engine.getEntity(collider2.node.getEntityID()).get(VelocityComponent.class) == null) {
                                GameData.world.collisionColliderPairs.put(cp, false);
                                continue;
                            }
                            GameData.world.collisionColliderPairs.put(cp, true);
                        } else if (!GameData.world.collisionColliderPairs.get(cp)) continue;
                    } else if (grid == hitBoxGrid) {
                        if (!GameData.world.hitBoxColliderPairs.containsKey(cp)) {
                            // compute early returns
                            if (Engine.getEntity(collider1.node.getEntityID()).get(DamageComponent.class) == null && Engine.getEntity(collider2.node.getEntityID()).get(DamageComponent.class) == null) {
                                GameData.world.hitBoxColliderPairs.put(cp, false);
                                continue;
                            }
                            LayerComponent layer1 = Engine.getEntity(collider1.node.getEntityID()).get(LayerComponent.class);
                            LayerComponent layer2 = Engine.getEntity(collider2.node.getEntityID()).get(LayerComponent.class);
                            // if they are on the same layer
                            if (layer1 != null && layer2 != null && (layer1.layer.value() & layer2.layer.value()) != 0) {
                                GameData.world.hitBoxColliderPairs.put(cp, true);
                                continue;
                            }
                        } else if (!GameData.world.hitBoxColliderPairs.get(cp)) continue;
                    }

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

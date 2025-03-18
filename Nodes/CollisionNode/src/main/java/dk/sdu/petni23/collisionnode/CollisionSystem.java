package dk.sdu.petni23.collisionnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.misc.Manifold;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;

import java.util.ArrayList;
import java.util.List;

public class CollisionSystem implements ISystem, IPluginService
{
    private static final List<CollisionNode>[][] collisionGrid = (ArrayList<CollisionNode>[][])new ArrayList[GameData.worldSize][GameData.worldSize];
    @Override
    public void update(double deltaTime)
    {
        clearGrid();
        populateGrid();
        GameData.world.manifolds.clear();
        createManifolds();

        for (var m :  GameData.world.manifolds) {
            if (checkCollision(m)) {
                resolveCollision(m);
            }
        }
    }

    @Override
    public int getPriority()
    {
        return Priority.POSTPROCESSING.get();
    }

    @Override
    public void start()
    {
        // initialize collision grid
        for (int i = 0; i < collisionGrid.length; i++) {
            for (int j = 0; j < collisionGrid[i].length; j++) {
                collisionGrid[i][j] = new ArrayList<>();
            }
        }
    }

    @Override
    public void stop()
    {

    }

    private void clearGrid() {
        for (int i = 0; i < collisionGrid.length; i++) {
            for (int j = 0; j < collisionGrid[i].length; j++) {
                collisionGrid[i][j].clear();
            }
        }
    }

    private void addNodeToGrid(CollisionNode node) {
        int x = (int) (node.positionComponent.getPosition().x + GameData.worldSize / 2);
        int y = (int) (-node.positionComponent.getPosition().y + GameData.worldSize / 2);
        if (x == GameData.worldSize) x--;
        if (y == GameData.worldSize) y--;

        collisionGrid[y][x].add(node);
    }

    private void populateGrid() {
        for (CollisionNode node : Engine.getNodes(CollisionNode.class)) {
            addNodeToGrid(node);
        }
    }

    private void createManifolds() {
        for (int x = 0; x < collisionGrid.length; x++) {
            for (int y = 0; y < collisionGrid[x].length; y++) {
                // for each entity in this tile
                for (CollisionNode n1 : collisionGrid[y][x]) {
                    // loop through all neighboring tiles
                    int startX = (x == 0) ? 0 : x-1;
                    int startY = (y == 0) ? 0 : y-1;
                    int endX = (x == collisionGrid.length - 1) ? x : x+1;
                    int endY = (y == collisionGrid[x].length - 1) ? y : y+1;

                    for (int i = startX; i <= endX; i++) {
                        for (int j = startY; j <= endY; j++) {
                            // loop through all entities in this tile
                            for (CollisionNode n2 : collisionGrid[j][i]) {
                                if (n1 == n2)
                                    continue;
                                // if both are static
                                if (n1.velocityComponent == null && n2.velocityComponent == null)
                                    continue;

                                Manifold c = new Manifold(n1, n2);
                                if (!GameData.world.manifolds.contains(c)) GameData.world.manifolds.add(c);
                            }
                        }
                    }
                }
            }
        }
    }

    private void resolveCollision(Manifold m) {
        Vector2D pen = m.normal.getMultiplied(m.penetration);
        CollisionNode a = (CollisionNode) m.a;
        CollisionNode b = (CollisionNode) m.b;
        // if one object is static, only the other is moved
        if (a.velocityComponent == null) {

            b.positionComponent.getPosition().subtract(pen);
            return;
        }
        else if (b.velocityComponent == null) {

            a.positionComponent.getPosition().subtract(pen);
            return;
        }
        pen.multiply(0.5);
        // move entities away from each other
        a.positionComponent.getPosition().subtract(pen);
        b.positionComponent.getPosition().add(pen);
    }

    private boolean checkCollision(Manifold m) {
        CollisionNode a = (CollisionNode) m.a;
        CollisionNode b = (CollisionNode) m.b;
        return switch (a.bodyComponent.getShape()) {
            case OvalShape s -> switch (b.bodyComponent.getShape()) {
                case OvalShape c -> OvalvsOval(m);
                case AABBShape c -> OvalvsAABB(m);
                default -> throw new IllegalStateException("Unexpected value: " + b.bodyComponent.getShape());
            };
            case AABBShape s -> switch (b.bodyComponent.getShape()) {
                case OvalShape c -> AABBvsOval(m);
                case AABBShape c -> AABBvsAABB(m);
                default -> throw new IllegalStateException("Unexpected value: " + b.bodyComponent.getShape());
            };
            default -> throw new IllegalStateException("Unexpected value: " + a.bodyComponent.getShape());
        };
    }

    private boolean OvalvsOval(Manifold m) {
        CollisionNode an = (CollisionNode) m.a;
        CollisionNode bn = (CollisionNode) m.b;
        OvalShape ac = (OvalShape) an.bodyComponent.getShape();
        OvalShape bc = (OvalShape) bn.bodyComponent.getShape();

        Vector2D n = bn.positionComponent.getPosition().getSubtracted(an.positionComponent.getPosition());
        double aRadius = ac.getRadius(n);
        double bRadius = bc.getRadius(n);

        double distSq = n.getLengthSq();
        double sumRad = aRadius + bRadius;
        // if collision
        if (distSq < sumRad * sumRad) {
            m.normal = n.getNormalized();
            double penLen = Math.sqrt(distSq);
            double d1 = penLen - aRadius;
            m.penetration = bRadius - d1;
            m.collide = true;
            return true;
        }
        m.collide = false;
        return false;
    }

    private boolean AABBvsAABB(Manifold m) {
        CollisionNode an = (CollisionNode) m.a;
        CollisionNode bn = (CollisionNode) m.b;
        AABBShape ab = (AABBShape) an.bodyComponent.getShape();
        AABBShape bb = (AABBShape) bn.bodyComponent.getShape();

        Vector2D n = bn.positionComponent.getPosition().getSubtracted(an.positionComponent.getPosition());

        double aExtent = ab.width / 2;
        double bExtent = bb.width / 2;

        double xOverlap = aExtent + bExtent - Math.abs(n.x);

        if (xOverlap > 0) {
            aExtent = ab.height / 2;
            bExtent = bb.height / 2;

            double yOverlap = aExtent + bExtent - Math.abs(n.y);

            if (yOverlap > 0) {
                // find out which axis is the axis of least penetration
                if (xOverlap < yOverlap) {
                    // point towards b, knowing that n points towards from a to b
                    if (n.x < 0)
                        m.normal = new Vector2D(-1, 0);
                    else
                        m.normal = new Vector2D(1, 0);
                    m.penetration = xOverlap;
                }
                else {
                    // point towards b knowing that n points from this to b
                    if (n.y < 0)
                        m.normal = new Vector2D(0, -1);
                    else
                        m.normal = new Vector2D(0, 1);
                    m.penetration = yOverlap;
                }
                m.collide = true;
                return true;
            }
        }
        m.collide = false;
        return false;
    }

    private boolean AABBvsOval(Manifold m) {
        CollisionNode an = (CollisionNode) m.a;
        CollisionNode bn = (CollisionNode) m.b;
        AABBShape ab = (AABBShape) an.bodyComponent.getShape();
        OvalShape bc = (OvalShape) bn.bodyComponent.getShape();

        Vector2D n = bn.positionComponent.getPosition().getSubtracted(an.positionComponent.getPosition());
        Vector2D closest = new Vector2D(n);

        double xExtent = ab.width / 2;
        double yExtent = ab.height / 2;

        closest.x = Math.clamp(closest.x, -xExtent, xExtent);
        closest.y = Math.clamp(closest.y, -yExtent, yExtent);

        boolean inside = false;
        // circle is inside the AABB, so we need to clamp the circle's center to the closest edge
        if (n.equals(closest)) {
            inside = true;

            // find closest axis
            if (Math.abs(n.x) > Math.abs(n.y)) {
                // clamp to the closest extent
                if (closest.x > 0)
                    closest.x = xExtent;
                else
                    closest.x = -xExtent;
            }
            // y-axis is shorter
            else {
                // clamp to closest extent
                if (closest.y > 0)
                    closest.y = yExtent;
                else
                    closest.y = -yExtent;
            }
        }

        Vector2D normal = n.getSubtracted(closest);
        double d = normal.getLengthSq();
        normal.normalize();
        double r = bc.getRadius(normal);

        // early out if the radius is shorter than the distance to the closest point, and circle is not inside AABB
        if (d > r * r && !inside) {
            m.collide = false;
            return false;
        }

        d = Math.sqrt(d);


        // collision normal needs to be flipped to point outside if circle was inside the AABB
        if (inside)
            m.normal = normal.getMultiplied(-1);
        else
            m.normal = normal;
        m.penetration = r-d;

        m.collide = true;
        return true;
    }

    private boolean OvalvsAABB(Manifold m) {
        Node e = m.a;
        m.a = m.b;
        m.b = e;

        return AABBvsOval(m);
    }
}

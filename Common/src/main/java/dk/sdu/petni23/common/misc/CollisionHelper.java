package dk.sdu.petni23.common.misc;

import dk.sdu.petni23.common.components.collision.HasShapeComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.node.Node;

public class CollisionHelper
{
    public static boolean checkCollision(Manifold m) {
        if (!boundingBoxesCollide(m)) return false;

        return switch (m.aShape) {
            case OvalShape s -> switch (m.bShape) {
                case OvalShape c -> OvalvsOval(m);
                case AABBShape c -> OvalvsAABB(m);
                default -> throw new IllegalStateException("Unexpected value: " + m.bShape);
            };
            case AABBShape s -> switch (m.bShape) {
                case OvalShape c -> AABBvsOval(m);
                case AABBShape c -> AABBvsAABB(m);
                default -> throw new IllegalStateException("Unexpected value: " + m.bShape);
            };
            default -> throw new IllegalStateException("Unexpected value: " + m.aShape);
        };
    }

    private static boolean OvalvsOval(Manifold m) {
        OvalShape ao = (OvalShape) m.aShape;
        OvalShape bo = (OvalShape) m.bShape;
        Vector2D aPos = m.aPos.position.getAdded(m.aOffset);
        Vector2D bPos = m.bPos.position.getAdded(m.bOffset);
        Vector2D n = bPos.getSubtracted(aPos);
        double aRadius = ao.getRadius(n);
        double bRadius = bo.getRadius(n);

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

    private static boolean AABBvsAABB(Manifold m) {
        AABBShape ab = (AABBShape) m.aShape;
        AABBShape bb = (AABBShape) m.bShape;
        Vector2D aPos = m.aPos.position.getAdded(m.aOffset);
        Vector2D bPos = m.bPos.position.getAdded(m.bOffset);

        Vector2D n = bPos.getSubtracted(aPos);

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

    private static boolean AABBvsOval(Manifold m) {
        AABBShape ab = (AABBShape) m.aShape;
        OvalShape bo = (OvalShape) m.bShape;
        Vector2D aPos = m.aPos.position.getAdded(m.aOffset);
        Vector2D bPos = m.bPos.position.getAdded(m.bOffset);

        Vector2D n = bPos.getSubtracted(aPos);
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
            if (xExtent - Math.abs(n.x) < yExtent - Math.abs(n.y)) {
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
        double r = bo.getRadius(normal);

        // early out if the radius is shorter than the distance to the closest point, and circle is not inside AABB
        if (d > r * r && !inside) {
            m.collide = false;
            return false;
        }

        d = Math.sqrt(d);


        // collision normal needs to be flipped to point outside if circle was inside the AABB
        if (inside) {
            m.normal = normal.getMultiplied(-1);
            m.penetration = d+r;
        }
        else {
            m.normal = normal;
            m.penetration = r-d;
        }


        m.collide = true;
        return true;
    }

    private static boolean OvalvsAABB(Manifold m) {
        Node e = m.a;
        m.a = m.b;
        m.b = e;

        Shape s = m.aShape;
        m.aShape = m.bShape;
        m.bShape = s;

        PositionComponent p = m.aPos;
        m.aPos = m.bPos;
        m.bPos = p;

        Vector2D o = m.aOffset;
        m.aOffset = m.bOffset;
        m.bOffset = o;

        return AABBvsOval(m);
    }

    private static boolean boundingBoxesCollide(Manifold m) {
        var aPos = m.aPos.position.getAdded(m.aOffset);
        var bPos = m.bPos.position.getAdded(m.bOffset);
        var aMin = m.aShape.aabb.min(aPos);
        var aMax = m.aShape.aabb.max(aPos);
        var bMin = m.bShape.aabb.min(bPos);
        var bMax = m.bShape.aabb.max(bPos);

        return aMin.x < bMax.x &&
                aMax.x > bMin.x &&
                aMin.y < bMax.y &&
                aMax.y > bMin.y;
    }
}

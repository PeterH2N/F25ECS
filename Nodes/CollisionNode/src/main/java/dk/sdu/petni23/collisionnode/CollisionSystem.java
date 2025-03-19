package dk.sdu.petni23.collisionnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.misc.CollisionHelper;
import dk.sdu.petni23.common.misc.Manifold;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.services.IPhysicsSystem;

public class CollisionSystem implements IPhysicsSystem
{
    @Override
    public void step(double deltaTime)
    {
        for (var m :  GameData.world.collisionManifolds) {
            // neither object can move
            if (m.a.getComponent(VelocityComponent.class) == null && m.b.getComponent(VelocityComponent.class) == null)
                continue;
            if (CollisionHelper.checkCollision(m)) {
                resolveCollision(m);
            }
        }
    }

    private void resolveCollision(Manifold m) {
        Vector2D pen = m.normal.getMultiplied(m.penetration);
        assert(m.a instanceof CollisionNode && m.b instanceof CollisionNode);
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


}

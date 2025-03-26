import dk.sdu.petni23.collisionnode.BroadPhaseSystem;
import dk.sdu.petni23.collisionnode.CollisionNodeSPI;
import dk.sdu.petni23.collisionnode.CollisionSystem;
import dk.sdu.petni23.collisionnode.HitBoxNodeSPI;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IPhysicsSystem;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;

module CollisionSystem {
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.collisionnode;
    provides INodeSPI with CollisionNodeSPI, HitBoxNodeSPI;
    provides IPhysicsSystem with CollisionSystem;
    provides ISystem with BroadPhaseSystem;
    provides IPluginService with BroadPhaseSystem;
}
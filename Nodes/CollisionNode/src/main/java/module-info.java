import dk.sdu.petni23.collisionnode.CollisionNodeSPI;
import dk.sdu.petni23.collisionnode.CollisionSystem;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;

module CollisionNode {
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.collisionnode;
    provides INodeSPI with CollisionNodeSPI;
    provides ISystem with CollisionSystem;
    provides IPluginService with CollisionSystem;
}
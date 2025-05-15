
import dk.sdu.petni23.connectionsystem.ConnectingCollisionNodeSPI;
import dk.sdu.petni23.connectionsystem.ConnectingCollisionSystem;
import dk.sdu.petni23.connectionsystem.ConnectingSpriteNodeSPI;
import dk.sdu.petni23.connectionsystem.ConnectingSpriteSystem;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;


module ConnectionSystem {
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.connectionsystem;
    provides INodeSPI with ConnectingSpriteNodeSPI, ConnectingCollisionNodeSPI;
    provides ISystem with ConnectingSpriteSystem, ConnectingCollisionSystem;
}
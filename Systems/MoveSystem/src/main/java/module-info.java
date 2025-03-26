
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IPhysicsSystem;
import dk.sdu.petni23.movenode.MoveNodeSPI;
import dk.sdu.petni23.movenode.MoveSystem;

module MoveSystem {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    provides IPhysicsSystem with MoveSystem;
    provides INodeSPI with MoveNodeSPI;
    exports dk.sdu.petni23.movenode;
}
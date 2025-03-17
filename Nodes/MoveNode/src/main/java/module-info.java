
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IProcessingSystem;
import dk.sdu.petni23.movenode.MoveNodeSPI;
import dk.sdu.petni23.movenode.MoveSystem;

module MoveNode {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    provides IProcessingSystem with MoveSystem;
    provides INodeSPI with MoveNodeSPI;
    exports dk.sdu.petni23.movenode;
}
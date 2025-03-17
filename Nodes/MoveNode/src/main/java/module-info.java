import dk.sdu.petni23.common.node.INodeSPI;
import dk.sdu.petni23.common.services.IProcessingSystem;
import dk.sdu.petni23.movenode.MoveNodeSPI;
import dk.sdu.petni23.movenode.MoveSystem;

module MoveNode {
    requires javafx.graphics;
    requires Common;
    provides IProcessingSystem with MoveSystem;
    provides INodeSPI with MoveNodeSPI;
    exports dk.sdu.petni23.movenode;
}
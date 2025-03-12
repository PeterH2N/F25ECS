import dk.sdu.petni23.common.node.INodeSPI;
import dk.sdu.petni23.common.services.ISystem;
import dk.sdu.petni23.movenode.MoveNodeSPI;
import dk.sdu.petni23.movenode.MoveSystem;

module MoveNode {
    requires javafx.graphics;
    requires Common;
    provides ISystem with MoveSystem;
    provides INodeSPI with MoveNodeSPI;
    exports dk.sdu.petni23.movenode;
}
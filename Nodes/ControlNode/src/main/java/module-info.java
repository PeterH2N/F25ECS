import dk.sdu.petni23.controlnode.ControlSystem;
import dk.sdu.petni23.controlnode.ControlNodeSPI;
import dk.sdu.petni23.common.node.INodeSPI;
import dk.sdu.petni23.common.services.IProcessingSystem;

module ControlNode {
    requires javafx.graphics;
    requires Common;
    exports dk.sdu.petni23.controlnode;
    provides INodeSPI with ControlNodeSPI;
    provides IProcessingSystem with ControlSystem;
}
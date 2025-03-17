import dk.sdu.petni23.controlnode.ControlSystem;
import dk.sdu.petni23.controlnode.ControlNodeSPI;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IProcessingSystem;


module ControlNode {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.controlnode;
    provides INodeSPI with ControlNodeSPI;
    provides IProcessingSystem with ControlSystem;
}
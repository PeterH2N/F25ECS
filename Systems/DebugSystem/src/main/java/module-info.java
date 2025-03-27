

import dk.sdu.petni23.debugnode.DebugSystem;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;


module DebugSystem {
    requires Common;
    requires GameEngine;
    requires javafx.graphics;
    exports dk.sdu.petni23.debugnode;
    provides ISystem with DebugSystem;
}
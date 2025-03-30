

import dk.sdu.petni23.debugnode.DebugSystem;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;


module DebugSystem {
    requires Common;
    requires GameEngine;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    opens dk.sdu.petni23.debugnode.ui to javafx.fxml;
    exports dk.sdu.petni23.debugnode;
    provides ISystem with DebugSystem;
    provides IPluginService with DebugSystem;
}
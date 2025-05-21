import dk.sdu.petni23.common.ISpawn;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.gameflow.GameFlowSystem;
import dk.sdu.petni23.gameflow.SpawnNodeSPI;

module GameFlowSystem {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.google.gson;
    requires Common;
    requires GameEngine;
    uses ISpawn;
    opens dk.sdu.petni23.gameflow.ui to javafx.fxml;
    provides IPluginService with GameFlowSystem;
    provides ISystem with GameFlowSystem;
    provides INodeSPI with SpawnNodeSPI;
    exports dk.sdu.petni23.gameflow;
}

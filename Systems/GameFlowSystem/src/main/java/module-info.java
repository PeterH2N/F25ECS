import dk.sdu.petni23.common.ISpawn;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.gameflow.GameFlowSystem;
import dk.sdu.petni23.gameflow.SpawnNodeSPI;

module GameFlowSystem {
    requires javafx.graphics;
    requires javafx.fxml;
    requires Common;
    requires GameEngine;
    uses ISpawn;
    provides IPluginService with GameFlowSystem;
    provides ISystem with GameFlowSystem;
    provides INodeSPI with SpawnNodeSPI;
    exports dk.sdu.petni23.gameflow;
}

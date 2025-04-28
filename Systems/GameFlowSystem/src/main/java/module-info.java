import dk.sdu.petni23.common.ISpawn;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.gameflow.GameFlowSystem;
import dk.sdu.petni23.gameflow.SpawnNodeSPI;

module GameFlowSystem {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    uses ISpawn;
    provides ISystem with GameFlowSystem;
    provides INodeSPI with SpawnNodeSPI;
    exports dk.sdu.petni23.gameflow;
}

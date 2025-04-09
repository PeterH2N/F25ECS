import dk.sdu.petni23.aisystem.AINodeSPI;
import dk.sdu.petni23.aisystem.AISystem;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;


module AISystem {
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.aisystem;
    provides INodeSPI with AINodeSPI;
    provides ISystem with AISystem;
}
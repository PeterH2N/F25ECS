
import dk.sdu.petni23.durationnode.DurationNodeSPI;
import dk.sdu.petni23.durationnode.DurationSystem;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;



module DurationNode {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.durationnode;
    provides INodeSPI with DurationNodeSPI;
    provides ISystem with DurationSystem;
}
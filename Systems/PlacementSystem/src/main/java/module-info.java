import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.placement.PlacementSystem;
import dk.sdu.petni23.placement.PlacementNodeSPI;

module PlacementSystem {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    requires Structures;

    exports dk.sdu.petni23.placement;

    provides ISystem with PlacementSystem;
    provides INodeSPI with PlacementNodeSPI;
}

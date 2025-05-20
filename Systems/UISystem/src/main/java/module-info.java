import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.uisystem.HoverNodeSPI;
import dk.sdu.petni23.uisystem.HoverSystem;


module UISystem {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.uisystem;
    provides ISystem with HoverSystem;
    provides INodeSPI with HoverNodeSPI;
}
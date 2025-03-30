import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.inventorysystem.InventoryNodeSPI;
import dk.sdu.petni23.inventorysystem.InventorySystem;


module InventorySystem {
    requires Common;
    requires GameEngine;
    requires javafx.fxml;
    requires javafx.controls;
    opens dk.sdu.petni23.inventorysystem to javafx.fxml, javafx.controls;
    exports dk.sdu.petni23.inventorysystem;
    provides ISystem with InventorySystem;
    provides IPluginService with InventorySystem;
    provides INodeSPI with InventoryNodeSPI;
}
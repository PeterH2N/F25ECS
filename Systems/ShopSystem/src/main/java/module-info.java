import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.shopping.ShopSystem;
import dk.sdu.petni23.shopping.ShopNodeSPI;
module ShopSystem {
    requires Common;
    requires GameEngine;
    requires InventorySystem;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;

    opens dk.sdu.petni23.shopping to javafx.fxml;

    provides ISystem with ShopSystem;
    provides INodeSPI with ShopNodeSPI;
    provides IPluginService with ShopSystem;

    uses IEntitySPI;

    exports dk.sdu.petni23.shopping;
}

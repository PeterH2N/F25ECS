
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.itemnode.ItemNodeSPI;
import dk.sdu.petni23.itemnode.PickUpNodeSPI;
import dk.sdu.petni23.itemnode.PickupSystem;


module ItemSystem {
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.itemnode;
    provides INodeSPI with ItemNodeSPI, PickUpNodeSPI;
    provides ISystem with PickupSystem;
}
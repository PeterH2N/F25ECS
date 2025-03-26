
import dk.sdu.petni23.damagenode.DamageNodeSPI;
import dk.sdu.petni23.damagenode.DamageSystem;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;



module DamageSystem {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.damagenode;
    provides INodeSPI with DamageNodeSPI;
    provides ISystem with DamageSystem;
}
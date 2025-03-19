
import dk.sdu.petni23.actionnode.ActionNodeSPI;
import dk.sdu.petni23.actionnode.ActionSystem;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;


module ActionNode {
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.actionnode;
    provides INodeSPI with ActionNodeSPI;
    provides ISystem with ActionSystem;
}
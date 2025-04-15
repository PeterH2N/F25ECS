
import dk.sdu.petni23.bindingsystem.BindingNodeSPI;
import dk.sdu.petni23.bindingsystem.BindingSystem;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;


module BindingSystem {
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.bindingsystem;
    provides ISystem with BindingSystem;
    provides INodeSPI with BindingNodeSPI;
}
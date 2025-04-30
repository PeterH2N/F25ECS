module NpcSystem {
    requires Common;
    requires GameEngine;

    exports dk.sdu.petni23.npcnode;

    provides dk.sdu.petni23.gameengine.node.INodeSPI with dk.sdu.petni23.npcnode.NPCNodeSPI;
    provides dk.sdu.petni23.gameengine.services.ISystem with dk.sdu.petni23.npcnode.NPCSystem;
}

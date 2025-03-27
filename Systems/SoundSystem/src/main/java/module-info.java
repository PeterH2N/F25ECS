module SoundSystem {
    requires java.desktop;
    requires javafx.graphics;
    requires Common;
    requires GameEngine;

    exports dk.sdu.petni23.soundnode;

    provides dk.sdu.petni23.gameengine.node.INodeSPI with dk.sdu.petni23.soundnode.SoundNodeSPI;
    provides dk.sdu.petni23.gameengine.services.ISystem with dk.sdu.petni23.soundnode.SoundSystem;
}

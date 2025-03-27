import dk.sdu.petni23.soundnode.SoundNodeSPI;
import dk.sdu.petni23.soundnode.SoundSystem;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;

module SoundSystem {
    requires java.desktop;
    requires javafx.graphics;
    requires Common;
    requires GameEngine;

    exports dk.sdu.petni23.soundnode;

    provides INodeSPI with SoundNodeSPI;
    provides ISystem with SoundSystem;
}

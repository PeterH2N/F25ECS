import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.soundnode.FootStepSoundNodeSPI;
import dk.sdu.petni23.soundnode.SoundNodeSPI;
import dk.sdu.petni23.soundnode.SoundSystem;

module SoundSystem {
    requires java.desktop;
    requires javafx.graphics;
    requires Common;
    requires GameEngine;

    exports dk.sdu.petni23.soundnode;

    provides INodeSPI with SoundNodeSPI, FootStepSoundNodeSPI;
    provides ISystem with SoundSystem;
}

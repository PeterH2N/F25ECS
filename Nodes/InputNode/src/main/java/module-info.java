import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.IProcessingSystem;
import dk.sdu.petni23.inputnode.IOPlugin;
import dk.sdu.petni23.inputnode.IOSystem;

module InputNode {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    provides IProcessingSystem with IOSystem;
    provides IPluginService with IOPlugin;
}
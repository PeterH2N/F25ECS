import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.inputnode.IOPlugin;
import dk.sdu.petni23.inputnode.IOSystem;

module InputSystem {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    provides ISystem with IOSystem;
    provides IPluginService with IOPlugin;
}
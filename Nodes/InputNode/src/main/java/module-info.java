import dk.sdu.petni23.common.services.IPluginService;
import dk.sdu.petni23.common.services.IProcessingSystem;
import dk.sdu.petni23.inputnode.IOPlugin;
import dk.sdu.petni23.inputnode.IOSystem;


module InputNode {
    requires javafx.graphics;
    requires Common;
    provides IProcessingSystem with IOSystem;
    provides IPluginService with IOPlugin;
}
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.IPostProcessingSystem;
import dk.sdu.petni23.gameengine.services.IProcessingSystem;

module GameEngine {
    exports dk.sdu.petni23.gameengine;
    exports dk.sdu.petni23.gameengine.services;
    exports dk.sdu.petni23.gameengine.node;
    exports dk.sdu.petni23.gameengine.entity;
    uses INodeSPI;
    uses IProcessingSystem;
    uses IPostProcessingSystem;
    uses IPluginService;
    uses IEntitySPI;
}
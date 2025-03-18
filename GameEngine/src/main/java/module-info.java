import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IPhysicsSystem;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;

module GameEngine {
    exports dk.sdu.petni23.gameengine;
    exports dk.sdu.petni23.gameengine.services;
    exports dk.sdu.petni23.gameengine.node;
    exports dk.sdu.petni23.gameengine.entity;
    uses INodeSPI;
    uses ISystem;
    uses IPhysicsSystem;
    uses IPluginService;
    uses IEntitySPI;
}
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.rendernode.RenderNodeSPI;
import dk.sdu.petni23.rendernode.RenderSystem;

module RenderSystem {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.rendernode;
    provides INodeSPI with RenderNodeSPI;
    provides ISystem with RenderSystem;
    provides IPluginService with RenderSystem;
}
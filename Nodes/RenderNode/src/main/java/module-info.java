import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IProcessingSystem;
import dk.sdu.petni23.rendernode.RenderNodeSPI;
import dk.sdu.petni23.rendernode.RenderSystem;

module RenderNode {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.rendernode;
    provides INodeSPI with RenderNodeSPI;
    provides IProcessingSystem with RenderSystem;
}
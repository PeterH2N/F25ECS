import dk.sdu.petni23.common.node.INodeSPI;
import dk.sdu.petni23.common.services.IProcessingSystem;
import dk.sdu.petni23.rendernode.RenderNodeSPI;
import dk.sdu.petni23.rendernode.RenderSystem;

module RenderNode {
    requires javafx.graphics;
    requires Common;
    exports dk.sdu.petni23.rendernode;
    provides INodeSPI with RenderNodeSPI;
    provides IProcessingSystem with RenderSystem;
}
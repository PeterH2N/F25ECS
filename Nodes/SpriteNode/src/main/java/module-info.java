import dk.sdu.petni23.common.node.INodeSPI;
import dk.sdu.petni23.common.services.IProcessingSystem;
import dk.sdu.petni23.spritenode.SpriteNodeSPI;
import dk.sdu.petni23.spritenode.SpriteSystem;


module SpriteNode {
    requires javafx.graphics;
    requires Common;
    exports dk.sdu.petni23.spritenode;
    provides INodeSPI with SpriteNodeSPI;
    provides IProcessingSystem with SpriteSystem;
}
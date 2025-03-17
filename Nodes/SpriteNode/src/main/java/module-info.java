
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IProcessingSystem;
import dk.sdu.petni23.spritenode.SpriteNodeSPI;
import dk.sdu.petni23.spritenode.SpriteSystem;


module SpriteNode {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.spritenode;
    provides INodeSPI with SpriteNodeSPI;
    provides IProcessingSystem with SpriteSystem;
}
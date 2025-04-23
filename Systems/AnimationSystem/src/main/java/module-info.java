
import dk.sdu.petni23.animationnode.ConnectingSpriteNodeSPI;
import dk.sdu.petni23.animationnode.ConnectingSpriteSystem;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.animationnode.AnimationNodeSPI;
import dk.sdu.petni23.animationnode.AnimationSystem;


module AnimationSystem {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.animationnode;
    provides INodeSPI with AnimationNodeSPI, ConnectingSpriteNodeSPI;
    provides ISystem with AnimationSystem, ConnectingSpriteSystem;
}

import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.animationnode.AnimationNodeSPI;
import dk.sdu.petni23.animationnode.AnimationSystem;


module AnimationNode {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.animationnode;
    provides INodeSPI with AnimationNodeSPI;
    provides ISystem with AnimationSystem;
}

import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.IPhysicsSystem;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.movenode.*;

module MoveSystem {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    provides ISystem with RotationSystem, TrajectorySystem;
    provides IPhysicsSystem with MoveSystem;
    provides INodeSPI with MoveNodeSPI;
    exports dk.sdu.petni23.movenode;
}

import dk.sdu.petni23.durationnode.DurationNodeSPI;
import dk.sdu.petni23.durationnode.DurationSystem;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.healthnode.HealthNodeSPI;
import dk.sdu.petni23.healthnode.HealthSystem;


module LifeTimeNode {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    exports dk.sdu.petni23.durationnode;
    exports dk.sdu.petni23.healthnode;
    provides INodeSPI with DurationNodeSPI, HealthNodeSPI;
    provides ISystem with DurationSystem, HealthSystem;
}
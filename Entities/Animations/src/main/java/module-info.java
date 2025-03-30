
import dk.sdu.petni23.animations.DeathAnimationSPI;
import dk.sdu.petni23.animations.FoamAnimationSPI;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;

module Animations {
    requires GameEngine;
    requires Common;
    requires javafx.graphics;
    provides IEntitySPI with DeathAnimationSPI, FoamAnimationSPI;
}
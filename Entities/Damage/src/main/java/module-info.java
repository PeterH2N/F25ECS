import dk.sdu.petni23.damage.DamageSPI;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;

module Damage {
    requires GameEngine;
    requires Common;
    requires javafx.graphics;
    provides IEntitySPI with DamageSPI;
}
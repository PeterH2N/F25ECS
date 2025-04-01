import dk.sdu.petni23.gameengine.entity.IEntitySPI;

module Common {
    requires javafx.graphics;
    requires GameEngine;
    exports dk.sdu.petni23.common.shape;
    exports dk.sdu.petni23.common.util;
    exports dk.sdu.petni23.common;
    exports dk.sdu.petni23.common.misc;
    exports dk.sdu.petni23.common.spritesystem;
    exports dk.sdu.petni23.common.world;
    exports dk.sdu.petni23.common.enums;
    exports dk.sdu.petni23.common.components.collision;
    exports dk.sdu.petni23.common.components.movement;
    exports dk.sdu.petni23.common.components.actions;
    exports dk.sdu.petni23.common.components;
    exports dk.sdu.petni23.common.components.health;
    exports dk.sdu.petni23.common.components.rendering;
    exports dk.sdu.petni23.common.components.sound;
    exports dk.sdu.petni23.common.components.items;
    exports dk.sdu.petni23.common.components.inventory;
    exports dk.sdu.petni23.common.components.damage;
    uses IEntitySPI;
}
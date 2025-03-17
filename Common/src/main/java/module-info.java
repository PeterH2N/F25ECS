import dk.sdu.petni23.common.entity.IEntitySPI;
import dk.sdu.petni23.common.services.IPluginService;
import dk.sdu.petni23.common.services.IPostProcessingSystem;
import dk.sdu.petni23.common.services.IProcessingSystem;

module Common {
    uses dk.sdu.petni23.common.node.INodeSPI;
    uses IProcessingSystem;
    uses IEntitySPI;
    uses IPostProcessingSystem;
    uses IPluginService;
    requires javafx.graphics;
    exports dk.sdu.petni23.common.entity;
    exports dk.sdu.petni23.common.components;
    exports dk.sdu.petni23.common.node;
    exports dk.sdu.petni23.common.shape;
    exports dk.sdu.petni23.common.services;
    exports dk.sdu.petni23.common.util;
    exports dk.sdu.petni23.common;
    exports dk.sdu.petni23.common.misc;
    exports dk.sdu.petni23.common.spritesystem;
}
module Common {
    uses dk.sdu.petni23.common.node.INodeSPI;
    uses dk.sdu.petni23.common.services.ISystem;
    uses dk.sdu.petni23.common.entity.IEntitySPI;
    requires javafx.graphics;
    exports dk.sdu.petni23.common.data;
    exports dk.sdu.petni23.common.entity;
    exports dk.sdu.petni23.common.components;
    exports dk.sdu.petni23.common.node;
    exports dk.sdu.petni23.common.shape;
    exports dk.sdu.petni23.common.services;
    exports dk.sdu.petni23.common.util;
    exports dk.sdu.petni23.common;
}
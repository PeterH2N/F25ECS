module RespawnSystem {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    requires Common;
    requires GameEngine;

    exports dk.sdu.petni23.respawnsystem;

    provides dk.sdu.petni23.gameengine.services.ISystem
            with dk.sdu.petni23.respawnsystem.PlayerRespawnSystem;
    provides dk.sdu.petni23.gameengine.node.INodeSPI
            with dk.sdu.petni23.respawnsystem.RespawnNodeSPI;
}
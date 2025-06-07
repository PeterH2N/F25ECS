module GameOverSystem {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    requires Core;
    requires Common;
    requires GameEngine;

    exports dk.sdu.petni23.gameoversystem;

    opens dk.sdu.petni23.gameoversystem
            to javafx.fxml;

    provides dk.sdu.petni23.gameengine.services.ISystem
            with dk.sdu.petni23.gameoversystem.GameOverSystem;
    provides dk.sdu.petni23.gameengine.node.INodeSPI
            with dk.sdu.petni23.gameoversystem.GameOverNodeSPI;
}

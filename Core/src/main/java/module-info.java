module Core {
    requires javafx.controls;
    requires Common;
    requires GameEngine;
    requires java.desktop;
    requires javafx.fxml;
    opens dk.sdu.petni23.main to javafx.graphics;
    opens dk.sdu.petni23.ui to javafx.fxml;
    exports dk.sdu.petni23.main;
    exports dk.sdu.petni23.ui;
}
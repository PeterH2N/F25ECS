module Core {
    requires javafx.controls;
    requires Common;
    requires GameEngine;
    opens dk.sdu.petni23.main to javafx.graphics;
    exports dk.sdu.petni23.main;
}
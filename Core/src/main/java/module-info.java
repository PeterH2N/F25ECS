module Core {
    requires javafx.controls;
    requires Common;
    opens dk.sdu.petni23.main to javafx.graphics;
    exports dk.sdu.petni23.main;
}
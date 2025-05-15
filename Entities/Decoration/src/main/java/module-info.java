import dk.sdu.petni23.decoration.DecorationPlugin;
import dk.sdu.petni23.decoration.Decoration;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;

module Decoration {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    provides IPluginService with DecorationPlugin;
}
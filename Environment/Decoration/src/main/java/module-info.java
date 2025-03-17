import dk.sdu.petni23.decoration.DecorationPlugin;
import dk.sdu.petni23.decoration.DecorationSPI;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;

module Decoration {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    provides IEntitySPI with DecorationSPI;
    provides IPluginService with DecorationPlugin;
}
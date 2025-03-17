import dk.sdu.petni23.common.entity.IEntitySPI;
import dk.sdu.petni23.common.services.IPluginService;
import dk.sdu.petni23.decoration.DecorationPlugin;
import dk.sdu.petni23.decoration.DecorationSPI;

module Decoration {
    requires javafx.graphics;
    requires Common;
    provides IEntitySPI with DecorationSPI;
    provides IPluginService with DecorationPlugin;
}
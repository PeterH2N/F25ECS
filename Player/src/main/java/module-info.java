
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.player.PlayerSPI;
import dk.sdu.petni23.player.PlayerPlugin;

module Player {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    opens dk.sdu.petni23.player to javafx.graphics;
    uses PlayerSPI;
    provides IEntitySPI with PlayerSPI;
    provides IPluginService with PlayerPlugin;
}

import dk.sdu.petni23.enemy.EnemyPlugin;
import dk.sdu.petni23.enemy.TorchGoblinSPI;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.player.PlayerSPI;
import dk.sdu.petni23.player.PlayerPlugin;

module Character {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    opens dk.sdu.petni23.player to javafx.graphics;
    uses IEntitySPI;
    provides IEntitySPI with PlayerSPI, TorchGoblinSPI;
    provides IPluginService with PlayerPlugin, EnemyPlugin;
}
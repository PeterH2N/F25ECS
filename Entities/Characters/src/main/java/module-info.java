
import dk.sdu.petni23.common.ISpawn;
import dk.sdu.petni23.common.ISpawn;
import dk.sdu.petni23.enemy.EnemyPlugin;
import dk.sdu.petni23.enemy.Sheep;
import dk.sdu.petni23.enemy.TNTGoblin;
import dk.sdu.petni23.enemy.TorchGoblin;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.npc.Worker;
import dk.sdu.petni23.npc.WorkerPlugin;
import dk.sdu.petni23.player.Archer;
import dk.sdu.petni23.player.Knight;
import dk.sdu.petni23.player.Player;

module Character {
    requires javafx.graphics;
    requires Common;
    requires GameEngine;
    opens dk.sdu.petni23.player to javafx.graphics;
    uses IEntitySPI;
    provides IPluginService with Player, WorkerPlugin;
    provides IEntitySPI with Archer, Player, Knight, TNTGoblin, TorchGoblin, Sheep, Worker;
    provides ISpawn with EnemyPlugin;
}
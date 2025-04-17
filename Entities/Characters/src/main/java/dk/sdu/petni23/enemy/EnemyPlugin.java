package dk.sdu.petni23.enemy;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;

public class EnemyPlugin implements IPluginService {
    @Override
    public void start() {
        for (int i = 0; i < 20; i++) {
            Vector2D pos = GameData.randomWorldPos();
            if (pos != null)
                Engine.addEntity(TorchGoblin.create(pos));
            pos = GameData.randomWorldPos();
            if (pos != null)
                Engine.addEntity(TNTGoblin.create(pos));
            pos = GameData.randomWorldPos();
            if (pos != null)
                Engine.addEntity(Sheep.create(pos));
        }
    }

    @Override
    public void stop() {

    }

}

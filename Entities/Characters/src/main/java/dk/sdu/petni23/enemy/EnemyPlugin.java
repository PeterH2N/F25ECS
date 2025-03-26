package dk.sdu.petni23.enemy;

import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;

import java.util.Optional;

public class EnemyPlugin implements IPluginService
{
    @Override
    public void start()
    {
        for (int i = 0; i < 25; i++) {
            Engine.addEntity(TorchGoblin.create());
            Engine.addEntity(TNTGoblin.create());
        }
    }

    @Override
    public void stop()
    {

    }

}

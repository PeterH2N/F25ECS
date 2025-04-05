package dk.sdu.petni23.structures.mine;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;

public class MinePlugin implements IPluginService
{
    @Override
    public void start()
    {
        for (int i = 0; i < GameData.worldSize / 4; i++) {
            Vector2D pos = GameData.randomWorldPos();
            if (pos != null)
                Engine.addEntity(Mine.createMine(pos));
        }



    }

    @Override
    public void stop()
    {

    }
}

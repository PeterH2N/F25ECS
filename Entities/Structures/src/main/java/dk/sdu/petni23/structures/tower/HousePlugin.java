package dk.sdu.petni23.structures.tower;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;

public class HousePlugin implements IPluginService
{
    @Override
    public void start()
    {
        /*for (int i = 0; i < GameData.worldSize / 5; i++) {
            var pos = GameData.randomWorldPos();
            if (pos != null) {
                Engine.addEntity(House.create(pos));
            }
        }*/
    }

    @Override
    public void stop()
    {

    }
}

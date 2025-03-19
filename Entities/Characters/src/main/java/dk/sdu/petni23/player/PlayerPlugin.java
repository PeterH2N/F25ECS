package dk.sdu.petni23.player;



import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.services.IPluginService;

import java.util.ArrayList;
import java.util.List;

public class PlayerPlugin implements IPluginService
{
    @Override
    public void start()
    {
        Engine.addEntity(Player.create());
    }

    @Override
    public void stop()
    {

    }
}

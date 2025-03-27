package dk.sdu.petni23.decoration;


import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;

import java.util.Optional;

public class DecorationPlugin implements IPluginService
{
    final int numDecorations = 100;
    @Override
    public void start()
    {

        for (int i = 0; i < numDecorations; i++) {
            Vector2D pos = GameData.randomWorldPos();
            if (pos != null)
                Engine.addEntity(Decoration.create(pos));
        }
    }

    @Override
    public void stop()
    {

    }

}

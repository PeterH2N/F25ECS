package dk.sdu.petni23.structures.tree;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;

public class TreePlugin implements IPluginService
{
    @Override
    public void start()
    {
        for (int i = 0; i < 25; i++) {
            Vector2D pos = GameData.randomWorldPos();
            if (pos != null)
                Engine.addEntity(Tree.createTree(pos));
        }



    }

    @Override
    public void stop()
    {

    }
}

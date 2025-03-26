package dk.sdu.petni23.structures.tree;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;

import java.util.Objects;

public class TreePlugin implements IPluginService
{
    @Override
    public void start()
    {
        for (int i = 0; i < 25; i++) {
            double x = Math.random() * GameData.worldSize - (double) GameData.worldSize / 2;
            double y = Math.random() * GameData.worldSize - (double) GameData.worldSize / 2;

            Engine.addEntity(Tree.create(new Vector2D(x, y)));
        }



    }

    @Override
    public void stop()
    {

    }
}

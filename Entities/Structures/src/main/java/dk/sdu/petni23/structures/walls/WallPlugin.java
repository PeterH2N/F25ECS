package dk.sdu.petni23.structures.walls;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.services.IPluginService;

public class WallPlugin implements IPluginService {

    @Override
    public void start() {
        Entity e = StoneWall.create(new Vector2D(50,50));
        Engine.addEntity(e);
        GameData.setHand(e);
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }

}

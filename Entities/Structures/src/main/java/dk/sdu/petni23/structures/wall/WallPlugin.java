package dk.sdu.petni23.structures.wall;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;

public class WallPlugin implements IPluginService {

    @Override
    public void start() {
    }

    public void update() {
        if (GameData.getHand() == null) {
            GameData.setHand(Wall.create(new Vector2D(0, 0)));
            Engine.addEntity(GameData.getHand());
        }
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }

}

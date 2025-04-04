package dk.sdu.petni23.structures.archerTower;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;

public class ArcherTowerPlugin implements IPluginService {

    @Override
    public void start() {
        GameData.setHand(ArcherTower.create(new Vector2D(0, 0)));
        Engine.addEntity(GameData.getHand());
    }

    @Override
    public void stop() {
        // Nothing yet
    }
}

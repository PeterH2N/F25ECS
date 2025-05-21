package dk.sdu.petni23.structures.enemyhouses;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;

public class GoblinHousePlugin implements IPluginService{

    @Override
    public void start() {
        for (int i = 0; i < 5; i++) {
            Vector2D pos = GameData.randomWorldPos();
            if (pos != null)
                Engine.addEntity(GoblinHouse.createGoblinHouse(pos));
                System.out.println(pos);
        }
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }
    
}

package dk.sdu.petni23.enemy;

import java.util.ArrayList;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.common.ISpawn;

public class EnemyPlugin implements ISpawn {
    @Override
    public void start(ArrayList<Vector2D> sources,int entitesPerSource) {
        for (int i = 0;i<entitesPerSource;i++){
            for (Vector2D s : sources){
                Engine.addEntity(TNTGoblin.create(s));
                Engine.addEntity(TorchGoblin.create(s));
                //sheep removed for now
            }
        }
    }

    @Override
    public void stop() {

    }

}

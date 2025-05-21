package dk.sdu.petni23.enemy;

import java.util.ArrayList;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.common.ISpawn;

public class EnemyPlugin implements ISpawn {
    @Override
    public void start(Vector2D pos) {
        if(Math.random()>0.5){
            Engine.addEntity(TNTGoblin.create(pos));
        }else{
            Engine.addEntity(TorchGoblin.create(pos));
        }
    }

    @Override
    public void stop() {

    }

}

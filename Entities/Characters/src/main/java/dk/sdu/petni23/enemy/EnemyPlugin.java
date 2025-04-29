package dk.sdu.petni23.enemy;

import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.common.ISpawn;

public class EnemyPlugin implements ISpawn {
    @Override
    public void start(Vector2D pos) {
        Engine.addEntity(TNTGoblin.create(pos));
        Engine.addEntity(TorchGoblin.create(pos));
    }

    @Override
    public void stop() {

    }

}

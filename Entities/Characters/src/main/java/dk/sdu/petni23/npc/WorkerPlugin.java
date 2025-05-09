package dk.sdu.petni23.npc;

import dk.sdu.petni23.common.ISpawn;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;

import java.util.ArrayList;

public class WorkerPlugin implements IPluginService {

    @Override
    public void start() {
        System.out.println("✅ WorkerPlugin start() called");

        for (int i = 0; i < 0; i++) {
            Vector2D offset = new Vector2D(-5, i * 1.5); // forskyd X med 1.5
            Engine.addEntity(Worker.create(new Vector2D(0, 0).getAdded(offset)));
        }
        
    }

    @Override
    public void stop() {
    }
}

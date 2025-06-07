package dk.sdu.petni23.respawnsystem;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.GameData.RespawnRequest;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.ISystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PlayerRespawnSystem extends ISystem {

    private final LinkedList<TimerEntry> activeRespawns = new LinkedList<>();

    private record TimerEntry(double timeLeft, IEntitySPI.Type type, Vector2D spawnPosition) {
        TimerEntry tick(double delta) {
            return new TimerEntry(timeLeft - delta, type, spawnPosition);
        }
    }

    @Override
    public void update(double deltaTime) {
        // Tilføj nye respawn requests fra GameData
        while (!GameData.pendingRespawns.isEmpty()) {
            var r = GameData.pendingRespawns.poll();
            if (r != null) {
                activeRespawns.add(new TimerEntry(r.delay(), r.type(), r.position()));
                System.out.println("⏳ Scheduled respawn in " + (int) r.delay() + "s at " + r.position());
            }
        }

        // Opdater respawn timers
        List<TimerEntry> toRespawn = new ArrayList<>();
        List<TimerEntry> stillWaiting = new ArrayList<>();

        for (TimerEntry entry : activeRespawns) {
            var updated = entry.tick(deltaTime);
            if (updated.timeLeft <= 0) {
                toRespawn.add(updated);
            } else {
                stillWaiting.add(updated);
            }
        }

        activeRespawns.clear();
        activeRespawns.addAll(stillWaiting);

        // Udfør selve respawn
        for (TimerEntry entry : toRespawn) {
            if (GameData.world.nexus != null) {
                System.out.println("✅ Respawning entity of type: " + entry.type);

                Entity newEntity = Engine.getEntitySPI(entry.type).create(null);
                var pos = newEntity.get(PositionComponent.class);
                if (pos != null) {
                    pos.position.set(entry.spawnPosition.clone());
                }

                Engine.addEntity(newEntity);
                System.out.println("✅ Entity added at " + entry.spawnPosition);
            } else {
                System.out.println("💀 Nexus is destroyed. Skipping respawn.");
            }
        }
    }

    @Override
    public int getPriority() {
        return Priority.PROCESSING.get();
    }
}

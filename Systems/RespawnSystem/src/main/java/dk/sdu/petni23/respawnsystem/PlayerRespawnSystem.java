package dk.sdu.petni23.respawnsystem;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.ISystem;

public class PlayerRespawnSystem extends ISystem {

    @Override
    public void update(double deltaTime) {
        for (RespawnNode node : Engine.getNodes(RespawnNode.class)) {
            var respawn = node.respawnComponent;

            if (!respawn.active)
                continue;

            respawn.countdown -= deltaTime;
            System.out.println("⏳ Respawning in: " + (int) Math.ceil(respawn.countdown));

            if (respawn.countdown <= 0) {
                if (GameData.world.nexus != null) {
                    System.out.println("✅ Nexus is alive. Respawning...");

                    // Remove old entity
                    long oldId = node.getEntityID();
                    Engine.removeEntity(oldId);
                    System.out.println("❌ Removed old entity: " + oldId);

                    // Create new player and add to engine
                    Entity newPlayer = Engine.getEntitySPI(IEntitySPI.Type.PLAYER).create(null);
                    var posComponent = newPlayer.get(PositionComponent.class);
                    if (posComponent != null && respawn.spawnPosition != null) {
                        posComponent.position.set(respawn.spawnPosition.clone());
                    }

                    Engine.addEntity(newPlayer);
                    System.out.println("✅ New player entity created and added");

                } else {
                    System.out.println("💀 Nexus is destroyed. Game Over.");
                    Engine.removeEntity(node.getEntityID());
                }

                respawn.active = false;
                respawn.countdown = -1;
            }
        }
    }

    @Override
    public int getPriority() {
        return Priority.PROCESSING.get();
    }
}

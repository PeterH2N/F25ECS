package dk.sdu.petni23.npcnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.ControlComponent;
import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.ai.JobComponent;
import dk.sdu.petni23.common.components.inventory.InventoryComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.ISystem;

public class NPCSystem implements ISystem {

    @Override
    public void update(double deltaTime) {
        for (NPCNode node : Engine.getNodes(NPCNode.class)) {
            if (Engine.getEntity(node.getEntityID()).get(ControlComponent.class) != null)
                continue; // Skip players

            handleJob(node);
            updateAnimation(node);
        }
    }

    private void handleJob(NPCNode node) {
        var job = node.jobComponent;
        if (job == null || node.positionComponent == null)
            return;

        Vector2D pos = node.positionComponent.position;

        System.out.println("🚶 Moving toward: " + job.targetPosition);
        switch (job.currentState) {
            case GO_TO_MINE -> {
                if (job.targetPosition == null)
                    job.targetPosition = findNearestMine(pos);

                moveToward(node, job.targetPosition);

                if (pos.distance(job.targetPosition) < 0.5) {
                    job.currentState = JobComponent.State.MINING;
                    job.miningProgress = 0;
                }
            }

            case MINING -> {
                job.miningProgress++;

                Entity mine = findMineAt(job.targetPosition);
                if (mine != null) {
                    IEntitySPI damageSPI = Engine.getEntitySPI(IEntitySPI.Type.DAMAGE);
                    if (damageSPI != null) {
                        Engine.addEntity(damageSPI.create(mine));
                    }
                }

                if (job.miningProgress >= 3) {
                    job.currentState = JobComponent.State.GO_TO_PLAYER;
                    job.targetPosition = findPlayerPosition();
                }
            }

            case GO_TO_PLAYER -> {
                moveToward(node, job.targetPosition);
                if (pos.distance(job.targetPosition) < 0.5) {
                    job.currentState = JobComponent.State.DELIVER;
                }
            }

            case DELIVER -> {
                transferToPlayer(node);
                job.currentState = JobComponent.State.GO_TO_MINE;
                job.targetPosition = null;
            }

            case IDLE -> {
                // Do nothing
            }
        }
    }

    private void updateAnimation(NPCNode node) {
        var job = node.jobComponent;

        if (job != null && job.currentState == JobComponent.State.MINING) {
            node.spriteComponent.row = 5; // Mining animation
        } else if (node.inventoryComponent != null && !node.inventoryComponent.amounts.isEmpty()) {
            node.spriteComponent.row = 4; // Carry animation
        } else {
            node.spriteComponent.row = 1; // Normal walking animation
        }

        if (node.velocityComponent != null) {
            node.velocityComponent.speed = node.spriteComponent.row == 4 ? 1.5 : 3.0;
        }
    }

    private void moveToward(NPCNode node, Vector2D target) {
        if (node.velocityComponent == null || node.positionComponent == null || target == null)
            return;

        Vector2D pos = node.positionComponent.position;
        Vector2D toTarget = target.getSubtracted(pos);
        double distance = toTarget.getLength();

        if (distance < 0.1) {
            // 🚫 Stop movement når vi er tæt nok på
            node.velocityComponent.velocity.set(0, 0);
            return;
        }

        // ✅ Retning og fart
        Vector2D direction = toTarget.getNormalized();
        Vector2D velocity = direction.getMultiplied(node.velocityComponent.speed);
        node.velocityComponent.velocity.set(velocity);
    }

    private Vector2D findNearestMine(Vector2D from) {
        double closestDist = Double.MAX_VALUE;
        Vector2D closest = null;

        for (Entity e : Engine.getEntities()) {
            AIComponent ai = e.get(AIComponent.class);
            PositionComponent pos = e.get(PositionComponent.class);
            if (ai != null && ai.type == AIComponent.Type.MINE && pos != null) {
                double dist = pos.position.distance(from);
                if (dist < closestDist) {
                    closestDist = dist;
                    closest = pos.position;
                }
            }
        }

        return closest != null ? closest : new Vector2D(10, 5); // fallback
    }

    private Entity findMineAt(Vector2D pos) {
        for (Entity e : Engine.getEntities()) {
            AIComponent ai = e.get(AIComponent.class);
            PositionComponent minePos = e.get(PositionComponent.class);
            if (ai != null && ai.type == AIComponent.Type.MINE && minePos != null
                    && minePos.position.distance(pos) < 1.0) {
                return e;
            }
        }
        return null;
    }

    private Vector2D findPlayerPosition() {
        return GameData.camera.following.position;
    }

    private void transferToPlayer(NPCNode node) {
        InventoryComponent workerInventory = node.inventoryComponent;
        InventoryComponent playerInventory = GameData.playerInventory;

        if (workerInventory == null || playerInventory == null)
            return;

        workerInventory.amounts.forEach((type, qty) -> {
            playerInventory.amounts.merge(type, qty, Integer::sum);
        });

        workerInventory.amounts.clear();
    }

    @Override
    public int getPriority() {
        return Priority.PREPROCESSING.get();
    }
}

package dk.sdu.petni23.healthnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.score.ScoreComponent;
import dk.sdu.petni23.common.score.ScoreManager;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.ISystem;
import javafx.scene.effect.ColorAdjust;

public class HealthSystem extends ISystem {
    private final ColorAdjust white = new ColorAdjust(0.0, -0.5, 0.5, 0);

    @Override
    public void update(double deltaTime) {
        for (HealthNode node : Engine.getNodes(HealthNode.class)) {
            Entity entity = Engine.getEntity(node.getEntityID());
            if (entity == null)
                continue;

            // Hurt effect
            var sprite = entity.get(SpriteComponent.class);
            if (sprite != null) {
                if (GameData.getCurrentMillis() <= node.healthComponent.lastHurtTime + 200)
                    sprite.effect = white;
            }

            if (node.healthComponent.health <= 0) {
                if (node.healthComponent.onDeath != null)
                    node.healthComponent.onDeath.dispatch(node);

                // Only remove entity if it's not the player
                if (entity.getType() != IEntitySPI.Type.PLAYER) {
                    ScoreComponent scoreComponent = entity.get(ScoreComponent.class);
                    if (scoreComponent != null) {
                        ScoreManager.addScore(scoreComponent.scoreOnDeath);
                        System.out.println("Enemy killed! Added " + scoreComponent.scoreOnDeath +
                                " points. Total score: " + ScoreManager.getScore());
                    }

                    if (node.lootComponent != null) {
                        int numDrops = GameData.random.nextInt(node.lootComponent.minDrop,
                                node.lootComponent.maxDrop + 1);
                        for (int i = 0; i < numDrops; i++) {
                            node.lootComponent.drop.dispatch(node);
                        }
                    }

                    Engine.removeEntity(entity);
                }
            }
        }
    }

    @Override
    public int getPriority() {
        return Priority.POSTPROCESSING.get();
    }
}

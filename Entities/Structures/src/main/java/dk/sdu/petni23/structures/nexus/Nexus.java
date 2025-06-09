package dk.sdu.petni23.structures.nexus;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.Binding;
import dk.sdu.petni23.common.components.BindingComponent;
import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.gameflow.GameOverComponent;
import dk.sdu.petni23.common.components.health.HealthBarComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.structures.archerTower.Archer;
import dk.sdu.petni23.structures.tower.House;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Nexus implements IPluginService, IEntitySPI {
    private static final SpriteSheet spriteSheet;
    static {
        final int[] numFrames = { 1 };
        Image img = new Image(Objects.requireNonNull(House.class.getResourceAsStream("/structuresprites/Nexus.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }

    public static Entity createNexus() {
        var nexus = new Entity(Type.NEXUS);
        GameData.world.nexus = nexus;
        nexus.add(new PositionComponent(new Vector2D(0, 0)));
        var healthComponent = nexus.add(new HealthComponent(2000));
        healthComponent.onDeath = node -> GameData.world.nexus = null;

        var hitBoxShape = new AABBShape(4, 1);
        Vector2D offset = new Vector2D(0, 0.4);
        nexus.add(new HitBoxComponent(hitBoxShape, offset));
        nexus.add(new CollisionComponent(hitBoxShape, offset));

        nexus.add(new SpriteComponent(spriteSheet, new Vector2D(-0.5, -0.9)));
        nexus.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        nexus.add(new LayerComponent(LayerComponent.Layer.PLAYER));
        nexus.add(new AIComponent(AIComponent.Type.NEXUS, null, null));
        nexus.add(new HealthBarComponent(192, 20, Color.GREEN, 3.5));

        // add archer to tower
        Entity archer1 = Archer.create();
        Entity archer2 = Archer.create();
        // Engine.addEntity(archer1);
        // Engine.addEntity(archer2);
        // bind archer to tower
        var binding = nexus.add(new BindingComponent());
        Binding b1 = (towerE, archerE) -> {
            var towerPos = towerE.get(PositionComponent.class).position;
            archerE.get(PositionComponent.class).position.set(towerPos.getAdded(new Vector2D(-1.5, 1.95)));
            archerE.get(SpriteComponent.class).effect = towerE.get(SpriteComponent.class).effect;
        };
        Binding b2 = (towerE, archerE) -> {
            var towerPos = towerE.get(PositionComponent.class).position;
            archerE.get(PositionComponent.class).position.set(towerPos.getAdded(new Vector2D(1.5, 1.95)));
            archerE.get(SpriteComponent.class).effect = towerE.get(SpriteComponent.class).effect;
        };
        binding.bindings.put(archer1, b1);
        binding.bindings.put(archer2, b2);

        healthComponent.onDeath = node -> {
            var gameOverEntity = new Entity(null);
            var gameOver = new GameOverComponent();
            gameOver.triggered = true;
            gameOverEntity.add(gameOver);
            Engine.addEntity(gameOverEntity);

            GameData.world.nexus = null;
        };

        return nexus;
    }

    @Override
    public void start() {
        Engine.addEntity(createNexus());
    }

    @Override
    public void stop() {

    }

    @Override
    public Entity create(Entity parent) {
        return createNexus();
    }

    @Override
    public Type getType() {
        return Type.NEXUS;
    }
}

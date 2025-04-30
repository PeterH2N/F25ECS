package dk.sdu.petni23.structures.mine;

import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.health.DurationComponent;
import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.items.LootComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.sound.SoundEffect;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;

import java.util.Objects;

public class Mine implements IEntitySPI{
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = { 1 };
        Image img = new Image(Objects.requireNonNull(Mine.class.getResourceAsStream("/structuresprites/Mine.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }

    public static Entity createMine(Vector2D pos) {
        Entity mine = new Entity(Type.MINE);

        var position = new PositionComponent();
        position.position.set(pos);
        mine.add(position);

        var sprite = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -0.67));
        mine.add(sprite);

        mine.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

        var oval = new OvalShape((38d * 0.5) / 16, (10d * 0.5) / 16); // same width
        var collision = new CollisionComponent(oval, new Vector2D(0, 0)); // moved higher
        mine.add(collision);

        // Add HealthComponent first (without onHurt yet)
        var health = new HealthComponent(2000);
        health.invincible = true;
        mine.add(health);

        // Add LootComponent
        var stoneSPI = Engine.getEntitySPI(IEntitySPI.Type.SPAWN_STONE);
        var loot = new LootComponent(node -> {
            if (stoneSPI != null) {
                Engine.addEntity(stoneSPI.create(Engine.getEntity(node.getEntityID())));
            }
        });
        loot.maxDrop = 3;
        mine.add(loot);

        health.onHurt = node -> {
            System.out.println("onHurt triggered");

            Entity sound = new Entity(null);
            sound.add(new SoundComponent(SoundEffect.MINE_HIT, position.position, 150));
            sound.add(new DurationComponent(200, GameData.getCurrentMillis()));
            Engine.addEntity(sound);

            if (stoneSPI != null) {
                int numDrops = GameData.random.nextInt(3); // 0–2
                for (int i = 0; i < numDrops; i++) {
                    Entity drop = stoneSPI.create(mine);
                    Engine.addEntity(drop);
                }
            }
        };

        var rect = new AABBShape(1.5, 1); // slightly shorter
        var hitBox = new HitBoxComponent(rect, new Vector2D(0, 0.5)); // a bit higher
        mine.add(hitBox);

        mine.add(new LayerComponent(LayerComponent.Layer.NPC_TARGET));

        mine.add(new AnimationComponent());
        mine.add(new AIComponent(AIComponent.Type.MINE, null, null));

        return mine;
    }

    @Override
    public Entity create(Entity parent) {
        return createMine(Vector2D.ZERO);
    }

    @Override
    public Type getType() {
        return Type.MINE;
    }
}

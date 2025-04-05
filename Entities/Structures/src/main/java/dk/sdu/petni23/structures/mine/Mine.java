package dk.sdu.petni23.structures.mine;

import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.health.DurationComponent;
import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import javafx.scene.image.Image;

import java.util.Objects;

public class Mine {
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {1};
        Image img = new Image(Objects.requireNonNull(Mine.class.getResourceAsStream("/structuresprites/Mine.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }

    public static Entity createMine(Vector2D pos) {
        Entity mine = new Entity();

        var position = new PositionComponent();
        position.position.set(pos);
        mine.add(position);

        var sprite = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -0.875));
        mine.add(sprite);

        mine.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

        var oval = new OvalShape((24d * 0.5) / 64, (6d * 0.5) / 64);
        var collision = new CollisionComponent(oval);
        mine.add(collision);

        var health = new HealthComponent(2000);
        health.onHurt = node -> {
            Entity e = new Entity();
            e.add(new SoundComponent("mine_hit1", 150, 0.5));
            e.add(new DurationComponent(200, GameData.getCurrentMillis()));
            Engine.addEntity(e);
        };

        mine.add(health);



        var rect = new AABBShape(0.6, 0.7);
        var hitBox = new HitBoxComponent(rect, new Vector2D(0, 0.5));
        mine.add(hitBox);

        mine.add(new LayerComponent(LayerComponent.Layer.ALL));

        mine.add(new AnimationComponent());

        return mine;
    }
}

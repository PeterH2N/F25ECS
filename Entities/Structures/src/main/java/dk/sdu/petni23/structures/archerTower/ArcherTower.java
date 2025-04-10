package dk.sdu.petni23.structures.archerTower;

import java.util.Objects;

import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.PlacementComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import javafx.scene.image.Image;

public class ArcherTower {
    // Variables for hitbox and sprite sizes
    private static final double HITBOX_WIDTH = 1.5;
    private static final double HITBOX_HEIGHT = 1.55;

    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {1};
        Image img = new Image(Objects.requireNonNull(ArcherTower.class.getResourceAsStream("/structuresprites/Tower_Purple.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }

    public static Entity create(Vector2D pos){
        Entity tower = new Entity();

        var position = new PositionComponent();
        position.position.set(pos);
        tower.add(position);

        var origin = new Vector2D(-0.5, -0.5);
        var sprite = new SpriteComponent(spriteSheet, origin);
        tower.add(sprite);

        tower.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        tower.add(new LayerComponent(LayerComponent.Layer.ALL));

        Shape collisionShape = new AABBShape(HITBOX_WIDTH, HITBOX_HEIGHT);
        Shape hitBoxShape = new AABBShape(HITBOX_WIDTH, HITBOX_HEIGHT);
        var offset = new Vector2D(-0, -0.9);

        var collision = new CollisionComponent(collisionShape, offset);
        collision.active = false;
        tower.add(collision);
        var hitBox = new HitBoxComponent(hitBoxShape, offset);
        var healthComponent = new HealthComponent(500);
        var placement = new PlacementComponent(hitBox, healthComponent);
        tower.add(placement);

        return tower;
    }
}

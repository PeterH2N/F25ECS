package dk.sdu.petni23.structures.wall;

import java.util.Objects;

import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.PlacementComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.life.HealthComponent;
import dk.sdu.petni23.common.components.life.LayerComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Wall {
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {1};
        Image img = new Image(Objects.requireNonNull(Wall.class.getResourceAsStream("/structuresprites/stone_wall.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }

    public static Entity create(Vector2D pos){
        Entity wall = new Entity();

        //add positionn component to wall entity
        var position = new PositionComponent();
        position.position.set(pos);
        wall.add(position);

        //add sprite component
        dk.sdu.petni23.common.components.rendering.SpriteComponent sprite = new SpriteComponent(spriteSheet, pos);
        wall.add(sprite);

        wall.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

        wall.add(new LayerComponent(LayerComponent.Layer.ALL));

        var placementComponent = new PlacementComponent();
        wall.add(placementComponent);

        var health = new HealthComponent(20);
        wall.add(health);

        var rect = new AABBShape();
        rect.width = 0.6;
        rect.height = 0.7;
        var hitBox = new HitBoxComponent(rect);
        wall.add(hitBox);

        var rect2 = new AABBShape();
        rect2.width = 2;
        rect2.height = 1;
        var collision = new CollisionComponent(rect2);
        wall.add(collision);

        return wall;
    }
}

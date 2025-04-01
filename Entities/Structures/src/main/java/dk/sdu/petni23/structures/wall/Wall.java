package dk.sdu.petni23.structures.wall;

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

        Shape collisionShape = new AABBShape(2, 1);
        Shape hitBoxShape = new AABBShape(2, 1);

        var placementComponent = new PlacementComponent(collisionShape,hitBoxShape);
        wall.add(placementComponent);

        var health = new HealthComponent(20);
        wall.add(health);

        return wall;
    }
}

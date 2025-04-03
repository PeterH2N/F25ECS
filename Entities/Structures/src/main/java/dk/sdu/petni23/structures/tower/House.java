package dk.sdu.petni23.structures.tower;

import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import javafx.scene.image.Image;

import java.util.Objects;

public class House
{
    private static final SpriteSheet spriteSheet;
    static {
        final int[] numFrames = {1};
        Image img = new Image(Objects.requireNonNull(House.class.getResourceAsStream("/structuresprites/House.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }
    static Entity create(Vector2D pos) {
        Entity house = new Entity();
        var posC = new PositionComponent();
        posC.position.set(pos);
        house.add(posC);
        house.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        house.add(new SpriteComponent(spriteSheet, new Vector2D(-0.5, -0.85)));
        AABBShape aabb = new AABBShape(100d / 64d, 70d / 64d);
        house.add(new CollisionComponent(aabb, new Vector2D(0, 0.5)));

        return house;
    }
}

package dk.sdu.petni23.structures.shop;

import java.util.Objects;

import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.shop.ShopComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.structures.walls.StoneWall;
import javafx.scene.image.Image;

public class Shop {
    private static final SpriteSheet spriteSheet;


    static {
        final int[] numFrames = {1};
        Image img = new Image(Objects.requireNonNull(StoneWall.class.getResourceAsStream("/structuresprites/House_Shop.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }

    public static Entity create(Vector2D pos){
        Entity shop = new Entity(null);

        //add positionn component to wall entity
        var position = new PositionComponent();
        position.position.set(pos);
        shop.add(position);

        //add sprite component
        final var origin = new Vector2D(-0.5, -0.5);
        dk.sdu.petni23.common.components.rendering.SpriteComponent sprite = new SpriteComponent(spriteSheet, origin);
        shop.add(sprite);

        shop.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

        shop.add(new LayerComponent(LayerComponent.Layer.PLAYER));

        Shape collisionShape = new AABBShape(2, 1);
        var offset = new Vector2D(0, -0.5);
        Shape hitBoxShape = new AABBShape(2, 1);

        var collision = new CollisionComponent(collisionShape, offset);
        var hitBox = new HitBoxComponent(hitBoxShape, offset);
        shop.add(collision);
        shop.add(hitBox);

        var shopComponent = new ShopComponent();
        shop.add(shopComponent);


        return shop;
    }
}

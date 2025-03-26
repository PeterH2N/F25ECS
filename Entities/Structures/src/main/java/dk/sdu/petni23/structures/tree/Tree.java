package dk.sdu.petni23.structures.tree;

import dk.sdu.petni23.common.components.DisplayComponent;
import dk.sdu.petni23.common.components.SpriteComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.hp.HealthComponent;
import dk.sdu.petni23.common.components.hp.LayerComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import javafx.scene.image.Image;

import java.util.Objects;

public class Tree
{
    private static final SpriteSheet spriteSheet = new SpriteSheet();

    static {
        final int[] numFrames = {4};
        Image img = new Image(Objects.requireNonNull(Tree.class.getResourceAsStream("/structuresprites/Tree.png")));
        spriteSheet.init(img, numFrames, new Vector2D(img.getWidth() / 4, img.getHeight() / 3));
    }

    public static Entity create(Vector2D pos) {
        Entity tree = new Entity();

        var position = new PositionComponent();
        position.position.set(pos);
        tree.add(position);

        var sprite = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -0.875));
        tree.add(sprite);

        tree.add(new DisplayComponent(DisplayComponent.Order.FOREGROUND));

        var oval = new OvalShape();
        oval.a = (24d * 0.5) / 64;
        oval.b = (6d * 0.5) / 64;
        var collision = new CollisionComponent(oval);
        tree.add(collision);

        var health = new HealthComponent(20);

        tree.add(health);

        var rect = new AABBShape();
        rect.width = 0.6;
        rect.height = 0.7;
        var hitBox = new HitBoxComponent(rect);
        hitBox.yOffset = 0.5;
        tree.add(hitBox);

        tree.add(new LayerComponent(LayerComponent.Layer.ALL));


        return tree;

    }
}

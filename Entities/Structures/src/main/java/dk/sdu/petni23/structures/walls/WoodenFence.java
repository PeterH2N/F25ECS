package dk.sdu.petni23.structures.walls;

import dk.sdu.petni23.common.components.PlacementComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.ConnectingCollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.ConnectingSpriteComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.configreader.ConfigReader;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;

import java.util.Objects;

public class WoodenFence implements IEntitySPI {

    private static final SpriteSheet spriteSheet;


    static {
        final int[] numFrames = {4,4,4,4,4,4};
        Image img = new Image(Objects.requireNonNull(StoneWall.class.getResourceAsStream("/structuresprites/Fence.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(64, 64));
    }
    @Override
    public Entity create(Entity parent) {
        Entity woodenFence = new Entity(Type.WOODEN_FENCE);

        //add positionn component to wall entity
        var position = new PositionComponent();
        woodenFence.add(position);

        //add sprite component
        final var origin = new Vector2D(-0.485, -1.25);
        dk.sdu.petni23.common.components.rendering.SpriteComponent sprite = new SpriteComponent(spriteSheet, origin);
        woodenFence.add(sprite);
        sprite.column = 3;
        sprite.row = 4;

        woodenFence.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

        woodenFence.add(new LayerComponent(LayerComponent.Layer.PLAYER));

        Shape collisionShape = new AABBShape(0.3, 0.3);
        Shape hitBoxShape = new AABBShape(0.3, 0.3);
        var offset = new Vector2D(0, 0.5);

        var collision = new CollisionComponent(collisionShape, offset);
        collision.active = false;
        woodenFence.add(collision);
        var hitBox = new HitBoxComponent(hitBoxShape, offset);
        var health = new HealthComponent(ConfigReader.getItemHealth(Type.WOODEN_FENCE));
        var placementComponent = new PlacementComponent(hitBox, health);
        woodenFence.add(placementComponent);
        woodenFence.add(new ConnectingSpriteComponent(Type.WOODEN_FENCE));
        woodenFence.add(new ConnectingCollisionComponent(Type.WOODEN_FENCE));


        // band-aid fix for problem in placement system
        woodenFence.add(new VelocityComponent());
        placementComponent.toRemove.add(VelocityComponent.class);
        return woodenFence;
    }

    @Override
    public Type getType() {
        return Type.WOODEN_FENCE;
    }
}

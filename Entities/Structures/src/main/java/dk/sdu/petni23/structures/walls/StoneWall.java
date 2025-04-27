package dk.sdu.petni23.structures.walls;

import java.util.Objects;

import dk.sdu.petni23.common.components.collision.ConnectingCollisionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.ConnectingSpriteComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.PlacementComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.configreader.ConfigReader;
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
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;

public class StoneWall implements IEntitySPI{
    private static final SpriteSheet spriteSheet;


    static {
        final int[] numFrames = {4,4,4,4,4,4,4,4};
        Image img = new Image(Objects.requireNonNull(StoneWall.class.getResourceAsStream("/structuresprites/Tilemap_Elevation.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(64, 128));
    }

    public static Entity create(Vector2D pos){
        Entity stoneWall = new Entity(Type.STONE_WALL);

        //add positionn component to wall entity
        var position = new PositionComponent();
        position.position.set(pos);
        stoneWall.add(position);

        //add sprite component
        final var origin = new Vector2D(-0.5, -1);
        dk.sdu.petni23.common.components.rendering.SpriteComponent sprite = new SpriteComponent(spriteSheet, origin);
        stoneWall.add(sprite);
        sprite.column = 3;
        sprite.row = 4;

        stoneWall.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

        stoneWall.add(new LayerComponent(LayerComponent.Layer.PLAYER));

        Shape collisionShape = new AABBShape(1, 1);
        Shape hitBoxShape = new AABBShape(1, 1);
        var offset = new Vector2D(0, 0.5);

        var collision = new CollisionComponent(collisionShape, offset);
        collision.active = false;
        stoneWall.add(collision);
        var hitBox = new HitBoxComponent(hitBoxShape, offset);
        var health = new HealthComponent(ConfigReader.getItemHealth(Type.STONE_WALL));
        var placementComponent = new PlacementComponent(hitBox, health);
        stoneWall.add(placementComponent);
        stoneWall.add(new ConnectingCollisionComponent(Type.STONE_WALL));
        stoneWall.add(new ConnectingSpriteComponent(Type.STONE_WALL));

        // band-aid fix for problem in placement system
        stoneWall.add(new VelocityComponent());
        placementComponent.toRemove.add(VelocityComponent.class);
        //placementComponent.toRemove.add(CollisionComponent.class);
        return stoneWall;
    }

    @Override
    public Entity create(Entity parent) {
        return create(new Vector2D(0,0));
    }

    @Override
    public Type getType() {
        return Type.STONE_WALL;
    }
}

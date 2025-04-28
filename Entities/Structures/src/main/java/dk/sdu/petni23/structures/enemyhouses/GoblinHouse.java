package dk.sdu.petni23.structures.enemyhouses;

import java.util.Objects;

import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.gameflow.SpawnComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;

public class GoblinHouse implements IEntitySPI {
    private static final SpriteSheet spriteSheet;

    static{
        final int[] numFrames = { 1 };
        Image img = new Image(Objects.requireNonNull(GoblinHouse.class.getResourceAsStream("/structuresprites/Goblin_House.png")));
        spriteSheet = new SpriteSheet(img,numFrames,new Vector2D(img.getWidth(),img.getHeight()));
    }

    public static Entity createGoblinHouse(Vector2D pos){
        Entity goblinHouse = new Entity(IEntitySPI.Type.GOBLIN_HOUSE);
        var position = new PositionComponent();
        position.position.set(pos);
        goblinHouse.add(position);

        var sprite = new SpriteComponent(spriteSheet, new Vector2D(0,0));
        goblinHouse.add(sprite);

        goblinHouse.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

        var oval = new OvalShape((38d * 0.5) / 16, (10d * 0.5) / 16);
        var collision = new CollisionComponent(oval, new Vector2D(0, 0)); 
        goblinHouse.add(collision);

        var health = new HealthComponent(10000);
        health.invincible = true;
        goblinHouse.add(health);

        var rect = new AABBShape(1.5, 1);
        var hitBox = new HitBoxComponent(rect, new Vector2D(0, 0.5));
        goblinHouse.add(hitBox);
        goblinHouse.add(new LayerComponent(LayerComponent.Layer.ALL));

        var spawn = new SpawnComponent(true,pos,true);
        goblinHouse.add(spawn);

        return goblinHouse;
    }

    @Override
    public Entity create(Entity parent) {
        return createGoblinHouse(Vector2D.ZERO);
    }

    @Override
    public Type getType() {
        return Type.GOBLIN_HOUSE;
    }
}


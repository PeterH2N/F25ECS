package dk.sdu.petni23.structures.archerTower;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import dk.sdu.petni23.common.components.Binding;
import dk.sdu.petni23.common.components.BindingComponent;
import dk.sdu.petni23.common.components.ControlComponent;
import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.PlacementComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.configreader.ConfigReader;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;

public class ArcherTower implements IEntitySPI {
    // Variables for hitbox and sprite sizes
    private static final double HITBOX_WIDTH = 1.2;
    private static final double HITBOX_HEIGHT = 0.9;

    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {1};
        Image img = new Image(Objects.requireNonNull(ArcherTower.class.getResourceAsStream("/structuresprites/Tower_Purple.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }

    public static Entity create(Vector2D pos){
        Entity tower = new Entity();

        tower.add(new PositionComponent(pos));

        var origin = new Vector2D(-0.5, -0.867);
        var sprite = new SpriteComponent(spriteSheet, origin);
        tower.add(sprite);

        tower.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        tower.add(new LayerComponent(LayerComponent.Layer.PLAYER));

        Shape collisionShape = new OvalShape(0.63, 0.3);
        Shape hitBoxShape = new AABBShape(HITBOX_WIDTH, HITBOX_HEIGHT);
        var offset = new Vector2D(0, 0.2);

        var collision = new CollisionComponent(collisionShape, offset);
        collision.active = false;
        tower.add(collision);
        var hitBox = new HitBoxComponent(hitBoxShape, offset);
        var healthComponent = new HealthComponent(ConfigReader.getItemHealth(Type.TOWER_3.getValue()));
        var placement = new PlacementComponent(hitBox, healthComponent);
        tower.add(placement);

        tower.add(new AIComponent(AIComponent.Type.TOWER, null, null));

        // add archer to tower
        Entity archer = Archer.create();
        // ai component
        Engine.addEntity(archer);
        // bind archer position to tower
        var binding = tower.add(new BindingComponent());
        Binding b = (towerE, archerE) -> {
            var towerPos = towerE.get(PositionComponent.class).position;
            archerE.get(PositionComponent.class).position.set(towerPos.getAdded(new Vector2D(0, 1.85)));
        };
        binding.bindings.put(archer, b);
        healthComponent.onDeath = node -> Engine.removeEntity(archer);

        return tower;
    }

    @Override
    public Entity create(Entity parent) {
        return create(Vector2D.ZERO);
    }

    @Override
    public Type getType() {
        return Type.TOWER_3;
    }
}

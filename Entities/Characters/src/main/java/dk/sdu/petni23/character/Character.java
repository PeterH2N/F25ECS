package dk.sdu.petni23.character;

import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.life.HealthComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;

public class Character
{
    public static Entity create(Vector2D pos, double maxHP) {
        Entity character = new Entity();
        var position = new PositionComponent();
        position.position.set(pos);
        character.add(position);

        var direction = new DirectionComponent();
        character.add(direction);

        var velocity = new VelocityComponent();
        character.add(velocity);

        var oval = new OvalShape();
        oval.a = (21d * 0.5) / 64;
        oval.b = (4d * 0.5) / 64;
        var collision = new CollisionComponent(oval);
        character.add(collision);

        var rect = new AABBShape();
        rect.width = 0.6;
        rect.height = 0.7;
        var hitBox = new HitBoxComponent(rect);
        hitBox.yOffset = 0.5;
        character.add(hitBox);

        var deathAnimation = getSPI(IEntitySPI.Type.DEATH_ANIMATION);
        var health = new HealthComponent(maxHP);
        health.onDeath = node -> {
            assert deathAnimation != null;
            Engine.addEntity(deathAnimation.create(node));
        };
        character.add(health);


        character.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        character.add(new AnimationComponent());

        return character;
    }

    public static IEntitySPI getSPI(IEntitySPI.Type type) {
        for (var spi : Engine.getEntitySPIs()) {
            if (spi.getType() == type)
                return spi;
        }
        return null;
    }
}

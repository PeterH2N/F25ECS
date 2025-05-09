package dk.sdu.petni23.character;

import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.sound.SoundEffect;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;

public class Character {
    public static Entity create(Vector2D pos, double maxHP, SoundEffect damageSound, IEntitySPI.Type type) {
        Entity character = new Entity(type);
        var position = new PositionComponent();
        position.position.set(pos);
        character.add(position);

        var direction = new DirectionComponent();
        character.add(direction);

        var velocity = new VelocityComponent();
        character.add(velocity);

        var oval = new OvalShape((21d * 0.5) / 64, (4d * 0.5) / 64);
        var collision = new CollisionComponent(oval);
        character.add(collision);

        var rect = new AABBShape(0.6, 0.85);
        var hitBox = new HitBoxComponent(rect, new Vector2D(0, 0.4));
        character.add(hitBox);

        var deathAnimation = Engine.getEntitySPI(IEntitySPI.Type.DEATH_ANIMATION);
        var health = new HealthComponent(maxHP);
        health.onDeath = node -> {
            assert deathAnimation != null;
            Engine.addEntity(deathAnimation.create(Engine.getEntity(node.getEntityID())));
        };
        character.add(health);

        health.onHurt = node -> {
            if (damageSound == null) return;
            Entity e = new Entity(null);
            e.add(new SoundComponent(damageSound, position.position, 0));
            Engine.addEntity(e);
        };

        character.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        character.add(new AnimationComponent());

        return character;
    }
}

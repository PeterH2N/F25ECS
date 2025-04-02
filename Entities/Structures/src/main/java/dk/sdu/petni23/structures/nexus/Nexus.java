package dk.sdu.petni23.structures.nexus;

import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.common.util.Vector2D;

public class Nexus implements IEntitySPI
{
    private static SpriteSheet spriteSheet;
    static {
        final int[] numFrames = {1};
        //Image img = new Image(Objects.requireNonNull(House.class.getResourceAsStream("/structuresprites/Nexus.png")));
        //spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }
    public static Entity createNexus() {
        var nexus = new Entity();
        nexus.add(new HealthComponent(1000));
        var hitBoxShape = new AABBShape(2,2);
        nexus.add(new HitBoxComponent(hitBoxShape));
        nexus.add(new CollisionComponent(hitBoxShape));

        nexus.add(new SpriteComponent(spriteSheet, new Vector2D(-0.5,-0.5)));
        nexus.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

        return nexus;
    }

    @Override
    public Entity create(Entity parent)
    {
        return null;
    }

    @Override
    public Type getType()
    {
        return null;
    }
}

package dk.sdu.petni23.structures.nexus;

import dk.sdu.petni23.common.components.AIComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.structures.tower.House;
import javafx.scene.image.Image;

import java.util.Objects;

public class NexusPlugin implements IPluginService
{
    private static final SpriteSheet spriteSheet;
    static {
        final int[] numFrames = {1};
        Image img = new Image(Objects.requireNonNull(House.class.getResourceAsStream("/structuresprites/Nexus.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }
    public static Entity createNexus() {
        var nexus = new Entity();
        nexus.add(new PositionComponent(new Vector2D(0,0)));
        nexus.add(new HealthComponent(1000));
        var hitBoxShape = new AABBShape(4,1);
        Vector2D offset = new Vector2D(0,0.4);
        nexus.add(new HitBoxComponent(hitBoxShape, offset));
        nexus.add(new CollisionComponent(hitBoxShape, offset));

        nexus.add(new SpriteComponent(spriteSheet, new Vector2D(-0.5, -0.9)));
        //nexus.add(new AnimationComponent());
        nexus.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        nexus.add(new LayerComponent(LayerComponent.Layer.PLAYER));
        nexus.add(new AIComponent(AIComponent.Type.NEXUS, null, null));

        return nexus;
    }

    @Override
    public void start()
    {
        Engine.addEntity(createNexus());
    }

    @Override
    public void stop()
    {

    }
}

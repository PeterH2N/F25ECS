package dk.sdu.petni23.damage;

import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.common.components.damage.ThrowComponent;
import dk.sdu.petni23.common.components.movement.AngularMomentumComponent;
import dk.sdu.petni23.common.components.movement.TrajectoryComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.sound.SoundEffect;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.image.Image;

import java.util.Objects;

public class DynamiteSPI implements IEntitySPI {
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = { 6 };
        Image img = new Image(
                Objects.requireNonNull(DynamiteSPI.class.getResourceAsStream("/damagesprites/Dynamite.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 6, img.getHeight()));
    }

    public static Entity createDynamite(Vector2D pos) {
        Entity tnt = new Entity();

        var position = tnt.add(new PositionComponent(pos));
        tnt.add(new DirectionComponent());
        tnt.add(new DirectionComponent());
        tnt.add(new DisplayComponent(DisplayComponent.Layer.EFFECT));
        var spriteCOmponent = tnt.add(new SpriteComponent(spriteSheet, new Vector2D(-0.5, -0.5)));
        spriteCOmponent.rotateWithDirection = true;
        var animation = tnt.add(new AnimationComponent());
        animation.doMirrors = false;

        Entity e = new Entity();
        e.add(new SoundComponent(SoundEffect.DYNAMITE_THROW, position.position));
        Engine.addEntity(e);

        return tnt;
    }

    @Override
    public Entity create(Entity parent) {
        assert parent != null;
        Vector2D pos = parent.get(PositionComponent.class).position;
        Vector2D dir = parent.get(DirectionComponent.class).dir;
        double distance = parent.get(ThrowComponent.class).distance;
        Vector2D end = pos.getAdded(dir.getMultiplied(distance));
        Vector2D start = pos.getAdded(dir.getMultiplied(0.25));

        LayerComponent.Layer layer;
        LayerComponent layerComponent = parent.get(LayerComponent.class);
        layer = layerComponent == null ? LayerComponent.Layer.ALL : layerComponent.layer;

        var dynamite = createDynamite(pos);
        var am = dynamite.add(new AngularMomentumComponent());
        am.angularMomentum = dir.x > 0 ? -10 : 10;

        var explosionSPI = Engine.getEntitySPI(Type.EXPLOSION_ANIMATION);
        Dispatch onEnd = node -> {
            var circle = new OvalShape(0.5, 0.5);
            Engine.addEntity(DamageSPI.createDamageEntity(end, new HitBoxComponent(circle), layer, 15));
            assert explosionSPI != null;
            Engine.addEntity(explosionSPI.create(dynamite));
            Entity e = new Entity();
            e.add(new SoundComponent(SoundEffect.THROW_EXPLOSION, end));
            Engine.addEntity(e);
        };
        dynamite.add(new TrajectoryComponent(start, end, distance * 0.33, onEnd));
        return dynamite;
    }

    @Override
    public Type getType() {
        return Type.DYNAMITE;
    }
}

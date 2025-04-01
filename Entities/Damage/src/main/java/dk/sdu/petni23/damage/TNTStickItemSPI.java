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
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.util.Vector2D;
import javafx.scene.image.Image;

import java.util.Objects;

public class TNTStickItemSPI implements IEntitySPI
{
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {6};
        Image img = new Image(Objects.requireNonNull(TNTStickItemSPI.class.getResourceAsStream("/damagesprites/Dynamite.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 6, img.getHeight()));
    }


    public static Entity createStick(Vector2D start, Vector2D end, double dmg, double radius) {
        Entity tnt = new Entity();
        tnt.add(new PositionComponent(start));
        tnt.add(new DirectionComponent());
        tnt.add(new AngularMomentumComponent());
        tnt.add(new DirectionComponent());
        tnt.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        var spriteCOmponent = tnt.add(new SpriteComponent(spriteSheet, new Vector2D(0,0)));
        spriteCOmponent.rotateWithDirection = true;
        tnt.add(new AnimationComponent());

        Dispatch onEnd = node -> {
            var circle = new OvalShape(radius, radius);
            Engine.addEntity(DamageSPI.createDamageEntity(end, new HitBoxComponent(circle), LayerComponent.Layer.ALL, dmg));
        };
        tnt.add(new TrajectoryComponent(start, end, 0.5, onEnd));

        return tnt;
    }

    @Override
    public Entity create(Entity parent)
    {
        assert parent != null;
        Vector2D pos = parent.get(PositionComponent.class).position;
        Vector2D dir = parent.get(DirectionComponent.class).dir;
        Vector2D end = pos.getAdded(dir.getMultiplied(parent.get(ThrowComponent.class).distance));

        return createStick(pos, end, 10, 1);
    }

    @Override
    public Type getType()
    {
        return Type.DYNAMITE;
    }
}

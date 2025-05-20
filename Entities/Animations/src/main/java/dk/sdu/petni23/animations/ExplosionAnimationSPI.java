package dk.sdu.petni23.animations;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.health.DurationComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.image.Image;

import java.util.Objects;

public class ExplosionAnimationSPI implements IEntitySPI
{
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {9};
        Image img = new Image(Objects.requireNonNull(DeathAnimationSPI.class.getResourceAsStream("/animationsprites/Explosion.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 9, img.getHeight()));
    }
    @Override
    public Entity create(Entity parent)
    {
        var pos = Vector2D.ZERO;
        if (parent != null) {
            var positionComponent = parent.get(PositionComponent.class);
            assert positionComponent != null;
            pos = positionComponent.position;
        }

        Entity explosion = Animation.create(spriteSheet, new Vector2D(-0.5, -0.5), pos, DisplayComponent.Layer.FOREGROUND, Type.EXPLOSION_ANIMATION);
        explosion.add(new DurationComponent(900, GameData.getCurrentMillis()));
        explosion.add(new DisplayComponent(DisplayComponent.Layer.EFFECT));
        return explosion;
    }

    @Override
    public Type getType()
    {
        return Type.EXPLOSION_ANIMATION;
    }
}

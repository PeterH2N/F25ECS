package dk.sdu.petni23.animations;

import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.image.Image;

import java.util.Objects;

public class FoamAnimationSPI implements IEntitySPI
{
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {8};
        Image img = new Image(Objects.requireNonNull(FoamAnimationSPI.class.getResourceAsStream("/animationsprites/Foam.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 8, img.getHeight()));
    }
    public static Entity create() {
        Entity foam = Animation.create(spriteSheet, new Vector2D(-0.5, -0.5), Vector2D.ZERO, DisplayComponent.Layer.BACKGROUND, null);
        foam.get(AnimationComponent.class).createdAt -= (long) (Math.random() * 800);
        return foam;
    }

    @Override
    public Entity create(Entity parent)
    {
        return create();
    }

    @Override
    public Type getType()
    {
        return Type.FOAM_ANIMATION;
    }
}

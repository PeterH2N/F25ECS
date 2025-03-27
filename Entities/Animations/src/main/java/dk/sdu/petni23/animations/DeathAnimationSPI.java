package dk.sdu.petni23.animations;

import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.node.Node;
import javafx.scene.image.Image;

import java.util.Objects;

public class DeathAnimationSPI implements IEntitySPI
{
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {14};
        Image img = new Image(Objects.requireNonNull(DeathAnimationSPI.class.getResourceAsStream("/animationsprites/Death.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 7, img.getHeight() / 2));
    }
    @Override
    public Entity create(Node parent)
    {
        var pos = parent.getComponent(PositionComponent.class);
        assert pos != null;

        return Animation.create(1200,  spriteSheet, pos.position, DisplayComponent.Layer.FOREGROUND);
    }

    @Override
    public Type getType()
    {
        return Type.DEATH_ANIMATION;
    }
}

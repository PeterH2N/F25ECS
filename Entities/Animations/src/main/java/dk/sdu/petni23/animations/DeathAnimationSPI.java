package dk.sdu.petni23.animations;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.health.DurationComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.util.Vector2D;
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
    public Entity create(Entity parent)
    {
        var pos = parent.get(PositionComponent.class);
        assert pos != null;

        Entity death = Animation.create(spriteSheet, new Vector2D(-0.5, -0.67), pos.position, DisplayComponent.Layer.FOREGROUND);
        death.add(new DurationComponent(1200, GameData.getCurrentMillis()));
        return death;
    }

    @Override
    public Type getType()
    {
        return Type.DEATH_ANIMATION;
    }
}

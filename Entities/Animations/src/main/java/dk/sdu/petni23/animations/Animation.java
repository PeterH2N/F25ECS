package dk.sdu.petni23.animations;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.life.DurationComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;

public class Animation
{
    // helper function for animation entities
    static Entity create(long duration, SpriteSheet spriteSheet, Vector2D pos, DisplayComponent.Layer layer) {
        Entity animation = new Entity();
        animation.add(new DisplayComponent(layer));
        animation.add(new DurationComponent(duration, GameData.getCurrentMillis()));
        animation.add(new SpriteComponent(spriteSheet, new Vector2D(-0.5, -0.67)));
        var position = new PositionComponent();
        position.position.set(pos);
        animation.add(position);
        animation.add(new AnimationComponent());

        return animation;
    }
}

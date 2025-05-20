package dk.sdu.petni23.damage;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.health.DurationComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;

public class LandedArrowSPI implements IEntitySPI {

    static Entity landedArrow(Vector2D pos, Vector2D dir) {
        Entity arrow = new Entity(Type.LANDED_ARROW);
        arrow.add(new PositionComponent(pos));
        var direction = arrow.add(new DirectionComponent());
        direction.dir.set(dir);
        var sprite = arrow.add(new SpriteComponent(ArrowSPI.spriteSheet, new Vector2D(-0.765, -0.546)));
        sprite.row = 1;
        sprite.rotateWithDirection = true;
        arrow.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        arrow.add(new DurationComponent(60000, GameData.getCurrentMillis()));

        return arrow;
    }

    @Override
    public Entity create(Entity parent) {
        return landedArrow(Vector2D.ZERO, Vector2D.ZERO);
    }

    @Override
    public Type getType() {
        return Type.LANDED_ARROW;
    }
}

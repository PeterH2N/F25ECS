package dk.sdu.petni23.damage;

import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.damage.ThrowComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.TrajectoryComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;

import java.util.Objects;

public class ArrowSPI implements IEntitySPI {

    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = { 1 ,1 };
        Image img = new Image(
                Objects.requireNonNull(ArrowSPI.class.getResourceAsStream("/damagesprites/Arrow.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight() / 2));
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

        var arrow = new Entity();
        arrow.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        arrow.add(new PositionComponent(pos));
        var directionComponent = arrow.add(new DirectionComponent());
        directionComponent.dir.set(dir);

        var sprite = arrow.add(new SpriteComponent(spriteSheet, new Vector2D(-0.5, -0.5)));
        sprite.rotateWithDirection = true;

        Dispatch onEnd = node -> {
            var circle = new OvalShape(0.3, 0.3);
            Engine.addEntity(DamageSPI.createDamageEntity(end, new HitBoxComponent(circle), layer, 5));
        };
        arrow.add(new TrajectoryComponent(start, end, distance * 0.1, 25, onEnd));
        return arrow;
    }

    @Override
    public Type getType() {
        return Type.ARROW;
    }
}

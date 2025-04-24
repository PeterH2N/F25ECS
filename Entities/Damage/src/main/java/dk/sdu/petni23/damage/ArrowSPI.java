package dk.sdu.petni23.damage;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.AttackComponent;
import dk.sdu.petni23.common.components.damage.DamageComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.damage.ThrowComponent;
import dk.sdu.petni23.common.components.health.DurationComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.TrajectoryComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
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
        var hitBox = parent.get(HitBoxComponent.class);
        Vector2D offset = hitBox != null ? hitBox.offset : Vector2D.ZERO;
        Vector2D end = pos.getAdded(dir.getMultiplied(distance));
        Vector2D start = pos.getAdded(dir.getMultiplied(0.75)).getAdded(offset);

        double dmg = 3;
        var attackComponent = parent.get(AttackComponent.class);
        if (attackComponent != null) dmg *= attackComponent.strength;

        LayerComponent.Layer layer;
        LayerComponent layerComponent = parent.get(LayerComponent.class);
        layer = layerComponent == null ? LayerComponent.Layer.ALL : layerComponent.layer;



        var arrow = new Entity(null);
        arrow.add(new DisplayComponent(DisplayComponent.Layer.EFFECT));
        var positionComponent = arrow.add(new PositionComponent(pos));
        arrow.add(new VelocityComponent());
        var directionComponent = arrow.add(new DirectionComponent());
        directionComponent.dir.set(dir);

        var circle = new OvalShape(0.2, 0.2);
        arrow.add(new HitBoxComponent(circle));
        Dispatch onDoDamage = node -> {
            Engine.removeEntity(arrow);
        };
        var damage = arrow.add(new DamageComponent(dmg));
        damage.onDoDamage = onDoDamage;
        arrow.add(new LayerComponent(layer));

        var sprite = arrow.add(new SpriteComponent(spriteSheet, new Vector2D(-1, -0.546)));
        sprite.rotateWithDirection = true;

        Dispatch onEnd = node -> Engine.addEntity(landedArrow(positionComponent.position,directionComponent.dir));
        var traj = arrow.add(new TrajectoryComponent(start, end, distance * 0.1, 20, onEnd));
        traj.rotateWithSlope = true;
        return arrow;
    }

    private Entity landedArrow(Vector2D pos, Vector2D dir) {
        Entity arrow = new Entity(null);
        arrow.add(new PositionComponent(pos));
        var direction = arrow.add(new DirectionComponent());
        direction.dir.set(dir);
        var sprite = arrow.add(new SpriteComponent(spriteSheet, new Vector2D(-0.765, -0.546)));
        sprite.row = 1;
        sprite.rotateWithDirection = true;
        arrow.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        arrow.add(new DurationComponent(60000, GameData.getCurrentMillis()));

        return arrow;
    }

    @Override
    public Type getType() {
        return Type.ARROW;
    }
}

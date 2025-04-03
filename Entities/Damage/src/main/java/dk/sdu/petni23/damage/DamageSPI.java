package dk.sdu.petni23.damage;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.health.DurationComponent;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.DamageComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.damage.AttackComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.common.util.Vector2D;

public class DamageSPI implements IEntitySPI
{
    @Override
    public Entity create(Entity parent) {
        assert parent != null;
        double dmg = 0;
        var actionC = parent.get(ActionSetComponent.class);
        var parentPosition = parent.get(PositionComponent.class);
        var position = parentPosition == null ? new Vector2D(0,0) : new Vector2D(parentPosition.position);

        if (actionC != null && actionC.lastAction != null)   dmg += actionC.lastAction.strength;
        var directionComponent = parent.get(DirectionComponent.class);
        Vector2D dir = directionComponent == null ? new Vector2D(0,0) : directionComponent.dir;

        var offset = new Vector2D(0,0);
        var parentHitBox = parent.get(HitBoxComponent.class);
        if (parentHitBox != null) offset = parentHitBox.offset;
        OvalShape circle = new OvalShape(1, 1);
        var hitBox = new HitBoxComponent(circle, offset);

        // hitbox is influenced by attack component, and parent hitbox
        var attackComponent = parent.get(AttackComponent.class);
        if (attackComponent != null) {
            dmg *= attackComponent.strength;
            circle = new OvalShape(attackComponent.range, attackComponent.range);
            hitBox = new HitBoxComponent(circle, offset);
            position.add(dir.getMultiplied(attackComponent.range));
        }

        var layerComponent = parent.get(LayerComponent.class);
        var layer = layerComponent == null ? LayerComponent.Layer.ALL : layerComponent.layer;

        return createDamageEntity(position, hitBox, layer,dmg);
    }

    public static Entity createDamageEntity(Vector2D pos, HitBoxComponent hitBoxComponent, LayerComponent.Layer layer, double dmg) {
        Entity dmgE = new Entity();
        var duration = new DurationComponent(1, GameData.getCurrentMillis());
        dmgE.add(duration);
        var dmgComponent = dmgE.add(new DamageComponent());
        dmgComponent.damage = dmg;
        dmgE.add(new PositionComponent(pos));

        dmgE.add(hitBoxComponent);
        dmgE.add(new LayerComponent(layer));
        dmgE.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

        return dmgE;
    }

    @Override
    public Type getType()
    {
        return Type.DAMAGE;
    }
}

package dk.sdu.petni23.damage;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.DisplayComponent;
import dk.sdu.petni23.common.components.LifeTimeComponent;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.hp.DamageComponent;
import dk.sdu.petni23.common.components.hp.LayerComponent;
import dk.sdu.petni23.common.components.hp.StrengthComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.node.Node;

public class DamageSPI implements IEntitySPI
{
    @Override
    public Entity create(Node parent) {
        assert parent != null;
        Entity dmgE = new Entity();

        var duration = new LifeTimeComponent(1, GameData.getCurrentMillis());
        dmgE.add(duration);

        var dmg = new DamageComponent();
        var actionC = parent.getComponent(ActionSetComponent.class);

        if (actionC != null && actionC.lastAction != null)   dmg.damage += actionC.lastAction.strength;
        dmgE.add(dmg);

        var strengthC = parent.getComponent(StrengthComponent.class);
        if (strengthC != null) dmgE.add(parent.getComponent(StrengthComponent.class));

        var position = new PositionComponent();
        var posC = parent.getComponent(PositionComponent.class);
        if (posC != null) position.setPosition(posC.getPosition());
        dmgE.add(position);

        OvalShape circle = new OvalShape();
        circle.a = 1;
        circle.b = 1;
        var hitBox = new HitBoxComponent(circle);
        dmgE.add(hitBox);

        var layer = parent.getComponent(LayerComponent.class);
        if (layer != null) dmgE.add(layer);
        else dmgE.add(new LayerComponent(LayerComponent.Layer.ALL));

        dmgE.add(new DisplayComponent());

        return dmgE;
    }

    @Override
    public Type getType()
    {
        return Type.DAMAGE;
    }
}

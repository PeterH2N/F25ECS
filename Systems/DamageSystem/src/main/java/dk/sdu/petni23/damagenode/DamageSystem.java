package dk.sdu.petni23.damagenode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.life.HealthComponent;
import dk.sdu.petni23.common.components.life.LayerComponent;
import dk.sdu.petni23.common.misc.CollisionHelper;
import dk.sdu.petni23.common.misc.Manifold;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.services.ISystem;

import java.util.ArrayList;
import java.util.List;

public class DamageSystem implements ISystem
{
    @Override
    public void update(double deltaTime)
    {
        for (DamageNode node : Engine.getNodes(DamageNode.class)) {
            // get manifolds containing hit box
            List<Manifold> manifoldList = new ArrayList<>();
            for (Manifold m : GameData.world.hitBoxManifolds) {
                if (m.aShape == node.hitBoxComponent.hitBox || m.bShape == node.hitBoxComponent.hitBox)
                    manifoldList.add(m);
            }

            for (Manifold m : manifoldList) {
                Node cNode = m.aShape == node.hitBoxComponent.hitBox ? m.b : m.a;
                HealthComponent healthComponent = cNode.getComponent(HealthComponent.class);
                if (healthComponent == null) continue;
                if ((node.layerComponent.layer.value() & cNode.getComponent(LayerComponent.class).layer.value()) != 0) continue;
                if (!CollisionHelper.checkCollision(m)) continue;

                double dmg = node.damageComponent.damage;
                if (node.strengthComponent != null) dmg *= node.strengthComponent.strength;
                hurt(node, healthComponent, dmg);
            }
        }
    }

    @Override
    public int getPriority()
    {
        return Priority.PROCESSING.get();
    }

    public void hurt(Node node, HealthComponent hp, double dmg) {
        hp.health -= dmg;
        hp.lastHurtTime = GameData.getCurrentMillis();
        if (hp.onHurt != null) hp.onHurt.dispatch(node);
    }
}

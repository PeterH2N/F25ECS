package dk.sdu.petni23.common.components.damage;

import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.gameengine.Component;

public class DamageComponent extends Component
{
    public final double damage;
    public transient Dispatch onDoDamage = null;


    public DamageComponent(double damage) {
        this.damage = damage;
    }
}

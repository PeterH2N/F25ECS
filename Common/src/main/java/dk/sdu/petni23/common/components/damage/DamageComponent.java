package dk.sdu.petni23.common.components.damage;

import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.gameengine.Component;

public class DamageComponent extends Component
{
    public final double damage;
    public Dispatch onDoDamage = null;


    public DamageComponent(double damage) {
        this.damage = damage;
    }
}

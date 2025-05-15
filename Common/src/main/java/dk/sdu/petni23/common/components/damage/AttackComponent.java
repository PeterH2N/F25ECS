package dk.sdu.petni23.common.components.damage;

import dk.sdu.petni23.gameengine.Component;

public class AttackComponent extends Component
{
    public double strength = 1; // damage done is multiplied by this
    public double range = 1; // attack range
    public double speed = 1;

    public AttackComponent(double strength, double range)
    {
        this.strength = strength;
        this.range = range;
    }
}

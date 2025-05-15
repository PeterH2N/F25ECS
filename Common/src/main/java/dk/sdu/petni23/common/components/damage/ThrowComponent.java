package dk.sdu.petni23.common.components.damage;

import dk.sdu.petni23.gameengine.Component;

public class ThrowComponent extends Component
{
    public double min = 1;
    public final double range;
    public double distance = 0;

    public ThrowComponent(double range)
    {
        this.range = range;
    }
}

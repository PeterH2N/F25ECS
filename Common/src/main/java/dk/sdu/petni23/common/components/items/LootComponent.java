package dk.sdu.petni23.common.components.items;

import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.gameengine.Component;

public class LootComponent extends Component
{
    public int maxDrop = 1;
    public int minDrop = 1;
    public transient Dispatch drop = null;

    public LootComponent(Dispatch drop)
    {
        this.drop = drop;
    }

    public LootComponent() {}
}

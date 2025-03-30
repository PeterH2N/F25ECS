package dk.sdu.petni23.common.components.items;

import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.gameengine.Component;

public class LootComponent extends Component
{
    public int maxDrop = 1;
    public int minDrop = 1;
    public final Dispatch drop;

    public LootComponent(Dispatch drop)
    {
        this.drop = drop;
    }
}

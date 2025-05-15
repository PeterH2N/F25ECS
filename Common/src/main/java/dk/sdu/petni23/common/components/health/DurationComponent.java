package dk.sdu.petni23.common.components.health;

import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.gameengine.Component;

public class DurationComponent extends Component
{
    public long lifetime;
    public long createdAt;

    public transient Dispatch onDeath = null;

    public DurationComponent(long lifetime, long createdAt) {
        this.lifetime = lifetime;
        this.createdAt = createdAt;
    }
}

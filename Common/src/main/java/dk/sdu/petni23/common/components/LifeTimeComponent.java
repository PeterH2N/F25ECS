package dk.sdu.petni23.common.components;

import dk.sdu.petni23.gameengine.Component;

public class LifeTimeComponent extends Component
{
    public long lifetime;
    public long createdAt;

    public LifeTimeComponent(long lifetime, long createdAt) {
        this.lifetime = lifetime;
        this.createdAt = createdAt;
    }
}

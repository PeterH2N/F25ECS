package dk.sdu.petni23.common.components;

import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.gameengine.Component;

public class PlacementComponent extends Component {
    public final HitBoxComponent hitBoxComponent;
    public final HealthComponent healthComponent;

    public PlacementComponent(HitBoxComponent hitBoxComponent, HealthComponent healthComponent)
    {
        this.hitBoxComponent = hitBoxComponent;
        this.healthComponent = healthComponent;
    }


}

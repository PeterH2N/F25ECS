package dk.sdu.petni23.common.components;

import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.gameengine.Component;

public class PlacementComponent extends Component {

    public final CollisionComponent collisionComponent;
    public final HitBoxComponent hitBoxComponent;

    public PlacementComponent(CollisionComponent collisionComponent, HitBoxComponent hitBoxComponent)
    {
        this.collisionComponent = collisionComponent;
        this.hitBoxComponent = hitBoxComponent;
    }


}

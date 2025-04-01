package dk.sdu.petni23.common.components;

import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.gameengine.Component;

public class PlacementComponent extends Component{
    public Shape collisionShape;
    public Shape hitBoxShape;

    public PlacementComponent(Shape collisionShape, Shape hitBoxShape){
        this.collisionShape = collisionShape;
        this.hitBoxShape = hitBoxShape;
    }
}

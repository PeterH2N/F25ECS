package dk.sdu.petni23.common.shape;

import dk.sdu.petni23.gameengine.util.Vector2D;

public class AABBShape extends Shape
{
    public double width;
    public double height;

    @Override
    public Vector2D getBB()
    {
        return new Vector2D(width, height);
    }

    public AABBShape(double width, double height){
        this.width = width;
        this.height = height;
    }

    public AABBShape(){
        
    };
}

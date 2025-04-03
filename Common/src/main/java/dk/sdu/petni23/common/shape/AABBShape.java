package dk.sdu.petni23.common.shape;

public class AABBShape extends Shape
{
    public final double width;
    public final double height;

    public AABBShape(double width, double height){
        super(new AABB(width * 0.5, height * 0.5));
        this.width = width;
        this.height = height;
    }
}

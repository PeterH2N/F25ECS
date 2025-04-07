package dk.sdu.petni23.common.shape;


import dk.sdu.petni23.common.util.Vector2D;


public class OvalShape extends Shape
{
    public final double a;
    public final double b;

    public OvalShape(double a, double b)
    {
        super(new AABB(a, b));
        this.a = a;
        this.b = b;
    }

    public double getRadius(Vector2D n) {
        var normal = n.getNormalized();
        return (a * b) / Vector2D.sqrt((a*a*normal.y*normal.y)+(b*b*normal.x*normal.x));
    }
}

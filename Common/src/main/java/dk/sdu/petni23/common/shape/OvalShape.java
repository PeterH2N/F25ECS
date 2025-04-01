package dk.sdu.petni23.common.shape;


import dk.sdu.petni23.gameengine.util.Vector2D;


public class OvalShape extends Shape
{
    public double a = 0;
    public double b = 0;

    public OvalShape()
    {
    }

    public OvalShape(double a, double b)
    {
        this.a = a;
        this.b = b;
    }

    public double getRadius(Vector2D n) {
        var normal = n.getNormalized();
        return (a * b) / Math.sqrt((a*a*normal.y*normal.y)+(b*b*normal.x*normal.x));
    }

    @Override
    public Vector2D getBB()
    {
        return new Vector2D(2*a, 2*b);
    }
}

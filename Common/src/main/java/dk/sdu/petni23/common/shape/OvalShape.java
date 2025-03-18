package dk.sdu.petni23.common.shape;


import dk.sdu.petni23.common.util.Vector2D;


public class OvalShape extends Shape
{
    public double a = 0;
    public double b = 0;

    public double getRadius(Vector2D n) {
        double t = Math.atan2(n.y, n.x);
        if (t < 0 ) t += 2 * Math.PI;
        double sinT = Math.sin(t);
        double cosT = Math.cos(t);
        return (a * b) / Math.sqrt((a*a*sinT*sinT)+(b*b*cosT*cosT));
    }
}

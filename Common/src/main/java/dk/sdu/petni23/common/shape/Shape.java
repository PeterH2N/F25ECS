package dk.sdu.petni23.common.shape;

public abstract class Shape
{
    public final AABB aabb;

    protected Shape(AABB aabb)
    {
        this.aabb = aabb;
    }
}

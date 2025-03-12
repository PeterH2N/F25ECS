package dk.sdu.petni23.common.components;

import dk.sdu.petni23.common.shape.Shape;

public class BodyComponent extends Component
{
    private double mass;
    private double invMass;
    private Shape shape;

    public void setMass(double m) {
        mass = m;
        setInvMass(1.0 / m);
    }

    public double getMass()
    {
        return mass;
    }

    public double getInvMass()
    {
        return invMass;
    }

    public void setInvMass(double invMass)
    {
        this.invMass = invMass;
    }

    public Shape getShape()
    {
        return shape;
    }

    public void setShape(Shape shape)
    {
        this.shape = shape;
    }
}

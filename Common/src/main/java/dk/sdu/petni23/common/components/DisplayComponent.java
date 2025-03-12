package dk.sdu.petni23.common.components;

public class DisplayComponent extends Component
{
    private double[] polygonXs;
    private double[] polygonYs;

    public double[] getPolygonXs()
    {
        return polygonXs;
    }

    public void setPolygonXs(double[] polygonXs)
    {
        this.polygonXs = polygonXs;
    }

    public double[] getPolygonYs()
    {
        return polygonYs;
    }

    public void setPolygonYs(double[] polygonYs)
    {
        this.polygonYs = polygonYs;
    }
}

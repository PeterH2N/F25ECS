package dk.sdu.petni23.common.shape;

import dk.sdu.petni23.common.util.Vector2D;

public class AABB
{
    public final double hw, hh;

    public AABB(double hw, double hh)
    {
        this.hw = hw;
        this.hh = hh;
    }

    public Vector2D min(Vector2D pos) {
        return new Vector2D(pos.x - hw, pos.y - hh);
    }

    public Vector2D max(Vector2D pos) {
        return new Vector2D(pos.x + hw, pos.y + hh);
    }
}

package dk.sdu.petni23.player;

import dk.sdu.petni23.common.components.BodyComponent;
import dk.sdu.petni23.common.components.PositionComponent;
import dk.sdu.petni23.common.components.DisplayComponent;
import dk.sdu.petni23.common.components.VelocityComponent;
import dk.sdu.petni23.common.entity.Entity;
import dk.sdu.petni23.common.entity.IEntitySPI;
import dk.sdu.petni23.common.shape.CircleShape;
import dk.sdu.petni23.common.util.Vector2D;

public class PlayerSPI implements IEntitySPI
{

    @Override
    public Entity create()
    {
        Entity player = new Entity();

        var position = new PositionComponent();
        player.add(position);

        var velocity = new VelocityComponent();
        velocity.setVelocity(new Vector2D(0.5,0));
        player.add(velocity);

        var circle = new CircleShape();
        circle.setRadius(1);
        var body = new BodyComponent();
        body.setMass(100.0); // kgs
        body.setShape(circle);
        player.add(body);

        var render = new DisplayComponent();
        double[] xs = {-0.5, 0.5, -0.5};
        double[] ys = {0.25, 0, - 0.25};
        render.setPolygonXs(xs);
        render.setPolygonYs(ys);
        player.add(render);

        return player;
    }
}

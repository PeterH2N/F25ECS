package dk.sdu.petni23.character;

import dk.sdu.petni23.common.components.DisplayComponent;
import dk.sdu.petni23.common.components.collision.BodyComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;

public class Character
{
    public static Entity create(Vector2D pos) {
        Entity character = new Entity();
        var position = new PositionComponent();
        position.setPosition(pos);
        character.add(position);

        var direction = new DirectionComponent();
        character.add(direction);

        var velocity = new VelocityComponent();
        character.add(velocity);

        var oval = new OvalShape();
        oval.a = (21d * 0.5) / 64;
        oval.b = (4d * 0.5) / 64;
        var body = new BodyComponent();
        body.setShape(oval);
        character.add(body);

        var rect = new AABBShape();
        rect.width = 0.6;
        rect.height = 0.7;
        var hitBox = new HitBoxComponent(rect);
        hitBox.yOffset = 0.5;
        character.add(hitBox);

        character.add(new DisplayComponent());

        return character;
    }
}

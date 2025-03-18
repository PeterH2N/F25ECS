package dk.sdu.petni23.character;

import dk.sdu.petni23.common.components.DirectionComponent;
import dk.sdu.petni23.common.components.PositionComponent;
import dk.sdu.petni23.common.components.VelocityComponent;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;

import java.util.Vector;

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
        return character;
    }
}

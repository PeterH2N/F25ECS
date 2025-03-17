package dk.sdu.petni23.player;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.*;
import dk.sdu.petni23.common.entity.Entity;
import dk.sdu.petni23.common.entity.IEntitySPI;
import dk.sdu.petni23.common.shape.CircleShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Objects;

public class PlayerSPI implements IEntitySPI
{
    SpriteSheet spriteSheet = new SpriteSheet();

    public PlayerSPI() {
        final int[] numFrames = {6,6,6,6,6,6,6,6};
        Image img = new Image(Objects.requireNonNull(PlayerSPI.class.getResourceAsStream("/playersprites/spritesheet.png")));
        spriteSheet.init(img, 8, numFrames);
    }

    @Override
    public Entity create()
    {
        Entity player = new Entity();

        var position = new PositionComponent();
        player.add(position);

        var direction = new DirectionComponent();
        player.add(direction);

        var velocity = new VelocityComponent();
        player.add(velocity);
        var speed = new SpeedComponent();
        speed.speed = 3;
        player.add(speed);

        var circle = new CircleShape();
        circle.setRadius(1);
        var body = new BodyComponent();
        body.setMass(100.0); // kgs
        body.setShape(circle);
        player.add(body);

        var control = new ControlComponent();
        control.ULDR = new KeyCode[]{KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D};
        control.pointsToMouse = true;
        player.add(control);

        var spriteComponent = new SpriteComponent(this.spriteSheet, new Vector2D(-0.5, -0.66));
        player.add(spriteComponent);

        // set the camera to track the player
        GameData.camera.following = position;

        return player;
    }
}

package dk.sdu.petni23.player;

import dk.sdu.petni23.character.Character;
import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.animation.SpriteComponent;
import dk.sdu.petni23.common.components.collision.BodyComponent;
import dk.sdu.petni23.common.components.control.ControlComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.SpeedComponent;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Objects;

public class PlayerSPI implements IEntitySPI
{
    SpriteSheet spriteSheet = new SpriteSheet();

    public PlayerSPI() {
        final int[] numFrames = {6,6,6,6,6,6,6,6};
        Image img = new Image(Objects.requireNonNull(PlayerSPI.class.getResourceAsStream("/playersprites/Player.png")));
        spriteSheet.init(img, numFrames);
    }

    @Override
    public Entity create()
    {
        Entity player = Character.create(new Vector2D(0,0));

        var speed = new SpeedComponent();
        speed.speed = 3;
        player.add(speed);

        var oval = new OvalShape();
        oval.a = (21d * 0.5) / 64;
        oval.b = (4d * 0.5) / 64;
        var body = new BodyComponent();
        body.setShape(oval);
        player.add(body);

        var control = new ControlComponent();
        control.ULDR = new KeyCode[]{KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D};
        control.pointsToMouse = true;
        player.add(control);

        var spriteComponent = new SpriteComponent(this.spriteSheet, new Vector2D(-0.5, -127d / 192));
        player.add(spriteComponent);

        // set the camera to track the player
        GameData.camera.following = player.get(PositionComponent.class);

        return player;
    }
}

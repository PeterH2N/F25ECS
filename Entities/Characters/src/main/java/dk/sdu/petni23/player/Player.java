package dk.sdu.petni23.player;

import dk.sdu.petni23.character.Character;
import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.SpriteComponent;
import dk.sdu.petni23.common.components.ControlComponent;
import dk.sdu.petni23.common.components.actions.Action;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.hp.LayerComponent;
import dk.sdu.petni23.common.components.hp.StrengthComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.SpeedComponent;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Objects;

public class Player
{
    private static final SpriteSheet spriteSheet = new SpriteSheet();

    static {
        final int[] numFrames = {6,6,6,6,6,6,6,6};
        final int[] order = {0,1,6,2,4,7,3,5};
        Image img = new Image(Objects.requireNonNull(Player.class.getResourceAsStream("/playersprites/Player.png")));
        spriteSheet.init(img, numFrames, new Vector2D(img.getWidth() / 6, img.getHeight() / 8), order);
    }

    public static Entity create()
    {
        Entity player = Character.create(new Vector2D(0,0), 100);

        var speed = new SpeedComponent();
        speed.speed = 3;
        player.add(speed);

        var control = new ControlComponent();
        control.ULDR = new KeyCode[]{KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D};
        control.pointsToMouse = true;
        player.add(control);

        var spriteComponent = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -127d / 192));
        player.add(spriteComponent);

        IEntitySPI damageSPI = Character.getSPI(IEntitySPI.Type.DAMAGE);
        var actions = new ActionSetComponent();
        var action1 = new Action(Action.Directionality.QUAD);
        action1.animationIndex = 2;
        action1.duration = 600;
        action1.delay = 300;
        action1.onDispatch = node -> {
            assert damageSPI != null;
            Engine.addEntity(damageSPI.create(node));
        };
        action1.strength = 1;
        var action2 = new Action(Action.Directionality.QUAD);
        action2.animationIndex = 5;
        action2.duration = 600;
        action2.strength = 2;
        action2.delay = 300;
        action2.onDispatch = node -> {
            assert damageSPI != null;
            Engine.addEntity(damageSPI.create(node));
        };
        actions.actions.add(action1);
        actions.actions.add(action2);
        player.add(actions);

        player.add(new LayerComponent(LayerComponent.Layer.PLAYER));

        var strength = new StrengthComponent();
        strength.strength = 5;
        player.add(strength);

        // set the camera to track the player
        GameData.camera.following = player.get(PositionComponent.class);

        return player;
    }
}

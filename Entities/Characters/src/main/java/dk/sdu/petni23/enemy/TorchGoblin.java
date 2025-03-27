package dk.sdu.petni23.enemy;

import dk.sdu.petni23.character.Character;
import dk.sdu.petni23.common.components.actions.Action;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.life.LayerComponent;
import dk.sdu.petni23.common.components.movement.SpeedComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;

import java.util.Objects;

public class TorchGoblin
{
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {7,6,6,6,6};
        final int[] order = {0,1,3,2,4};
        Image img = new Image(Objects.requireNonNull(TorchGoblin.class.getResourceAsStream("/enemysprites/Goblin.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 7, img.getHeight() / 5), order);
    }

    public static Entity create(Vector2D pos)
    {
        Entity goblin = Character.create(pos, 30);

        var speed = new SpeedComponent();
        speed.speed = 2.5;
        goblin.add(speed);

        var spriteComponent = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -127d / 192));
        goblin.add(spriteComponent);

        IEntitySPI damageSPI = Character.getSPI(IEntitySPI.Type.DAMAGE);
        Action attack = new Action(Action.Directionality.QUAD);
        attack.delay = 300;
        attack.strength = 2;
        attack.onDispatch = node -> {
            assert damageSPI != null;
            Engine.addEntity(damageSPI.create(node));
        };
        var actionSet = new ActionSetComponent();
        actionSet.actions.add(attack);


        goblin.add(new LayerComponent(LayerComponent.Layer.ENEMY));

        return goblin;
    }
}

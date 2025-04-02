package dk.sdu.petni23.player;

import dk.sdu.petni23.character.Character;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.sound.FootstepSoundComponent;
import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.common.components.actions.Action;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.damage.AttackComponent;
import dk.sdu.petni23.common.components.movement.SpeedComponent;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Set;

public class Knight
{
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {6,6,6,6,6,6,6,6};
        final int[] order = {0,1,6,2,4,7,3,5};
        Image img = new Image(Objects.requireNonNull(Knight.class.getResourceAsStream("/playersprites/Player.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 6, img.getHeight() / 8), order);
    }

    public static Entity create(Vector2D pos) {
        Entity knight = Character.create(pos, 100, "knight_hurt1");

        var speed = new SpeedComponent();
        speed.speed = 3;
        knight.add(speed);

        var spriteComponent = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -127d / 192));
        knight.add(spriteComponent);

        IEntitySPI damageSPI = Engine.getEntitySPI(IEntitySPI.Type.DAMAGE);
        var actions = new ActionSetComponent();
        var action1 = new Action(Action.Directionality.QUAD);
        action1.animationIndex = 2;
        action1.duration = 600;
        action1.delay = 300;
        action1.onDispatch = node -> {
            assert damageSPI != null;
            Entity damageEntity = damageSPI.create(Engine.getEntity(node.getEntityID()));
            damageEntity.add(new SoundComponent("woosh1"));
            Engine.addEntity(damageEntity); // ✅ add the one you modified
        };
        action1.strength = 1;
        var action2 = new Action(Action.Directionality.QUAD);
        action2.animationIndex = 5;
        action2.duration = 600;
        action2.strength = 2;
        action2.delay = 300;
        action2.onDispatch = node -> {
            assert damageSPI != null;
            Entity damageEntity = damageSPI.create(Engine.getEntity(node.getEntityID()));
            damageEntity.add(new SoundComponent("woosh2",0,0.5));
            Engine.addEntity(damageEntity); // ✅ add the one you modified
        };
        actions.actions.add(action1);
        actions.actions.add(action2);
        knight.add(actions);

        knight.add(new LayerComponent(LayerComponent.Layer.PLAYER));

        knight.add(new FootstepSoundComponent("footstep_knight", Set.of(1, 4)));

        knight.add(new AttackComponent(5, 0.6));

        return knight;
    }
}

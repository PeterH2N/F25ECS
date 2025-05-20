package dk.sdu.petni23.player;

import dk.sdu.petni23.character.Character;
import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.sound.FootstepSoundComponent;
import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.common.components.actions.Action;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.damage.AttackComponent;
import dk.sdu.petni23.common.sound.SoundEffect;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Set;

import static dk.sdu.petni23.common.components.ai.AIComponent.Type.CHARACTER;

public class Knight implements IEntitySPI {
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {6,6,6,6,6,6,6,6};
        final int[] order = {0,1,6,2,4,7,3,5};
        Image img = new Image(Objects.requireNonNull(Knight.class.getResourceAsStream("/playersprites/Player.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 6, img.getHeight() / 8), order);
    }

    public static Entity create(Vector2D pos) {
        return create(pos, Type.KNIGHT);
    }

    public static Entity create(Vector2D pos, Type type) {
        Entity knight = Character.create(pos, 1000, SoundEffect.KNIGHT_HURT, type);

        knight.get(VelocityComponent.class).speed = 3;
        var position = knight.get(PositionComponent.class);

        //knight.get(HealthComponent.class).invincible = true;

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
            Entity sound = new Entity(null);
            sound.add(new SoundComponent(SoundEffect.WOOSH1, position.position));
            Engine.addEntity(sound);
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
            Engine.addEntity(damageEntity); // ✅ add the one you modified
            Entity sound = new Entity(null);
            sound.add(new SoundComponent(SoundEffect.WOOSH2, position.position));
            Engine.addEntity(sound);
        };
        actions.actions.add(action1);
        actions.actions.add(action2);
        knight.add(actions);

        knight.add(new LayerComponent(LayerComponent.Layer.PLAYER));

        knight.add(new FootstepSoundComponent(SoundEffect.FOOTSTEP_PLAYER, Set.of(1, 4)));

        var attack = knight.add(new AttackComponent(5, 0.6));
        attack.speed = 1.3;

        knight.add(new AIComponent(CHARACTER, null, null));

        return knight;
    }

    @Override
    public Entity create(Entity parent) {
        return create(new Vector2D(0,0));
    }

    @Override
    public Type getType() {
        return Type.KNIGHT;
    }
}

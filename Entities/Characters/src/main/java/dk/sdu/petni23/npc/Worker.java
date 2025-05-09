package dk.sdu.petni23.npc;

import dk.sdu.petni23.character.Character;
import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.ai.PathFindingComponent;
import dk.sdu.petni23.common.components.ai.WorkerComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.inventory.InventoryComponent;
import dk.sdu.petni23.common.components.inventory.PickUpComponent;
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

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static dk.sdu.petni23.common.components.ai.AIComponent.Type.CHARACTER;
import static dk.sdu.petni23.common.components.ai.AIComponent.Type.WORKER;

public class Worker implements IEntitySPI {
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = { 6, 6, 6, 6, 6, 6 };
        final int[] order = { 0, 1, 2, 3, 4, 5 };
        Image img = new Image(Objects.requireNonNull(Worker.class.getResourceAsStream("/npcsprites/Worker.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 6, img.getHeight() / 6), order);
    }

    public static Entity create(Vector2D pos) {
        return create(pos, Type.WORKER);
    }

    public static Entity create(Vector2D pos, Type type) {
        Entity worker = Character.create(pos, 10000, SoundEffect.KNIGHT_HURT, type);

        worker.get(VelocityComponent.class).speed = 3;
        var position = worker.get(PositionComponent.class);

        worker.get(HealthComponent.class).invincible = true;

        var spriteComponent = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -127d / 192));
        worker.add(spriteComponent);

        IEntitySPI damageSPI = Engine.getEntitySPI(Type.DAMAGE);
        var actions = new ActionSetComponent();
        var action1 = new Action(Action.Directionality.BI);
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
        actions.actions.add(action1);
        worker.add(actions);

        worker.add(new LayerComponent(LayerComponent.Layer.NPC)); // Needs new layer for the worker

        worker.add(new FootstepSoundComponent(SoundEffect.FOOTSTEP_PLAYER, Set.of(1, 4))); // Same sound as knight is
                                                                                           // fine.

        worker.add(new InventoryComponent(3, Type.STONE));

        var pickup = worker.add(new PickUpComponent());
        pickup.range = 1.0;

        var attack = worker.add(new AttackComponent(5, 0.6));
        attack.speed = 2;

        var ai = worker.add(new AIComponent(
                AIComponent.Type.WORKER, // hvad worker selv er
                List.of(AIComponent.Type.STONE, AIComponent.Type.MINE), // hvad den leder efter
                AIComponent.Priority.CLOSEST // hvordan den vælger mål
        ));
        ai.range = 1000;
        worker.add(new PathFindingComponent());
        worker.add(new WorkerComponent());

        return worker;

    }

    @Override
    public Entity create(Entity parent) {
        return create(new Vector2D(0, 0));
    }

    @Override
    public Type getType() {
        return Type.WORKER;
    }
}

package dk.sdu.petni23.structures.workerHut;

import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.common.components.actions.Action;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.ai.PathFindingComponent;
import dk.sdu.petni23.common.components.ai.WorkerComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.AttackComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.inventory.InventoryComponent;
import dk.sdu.petni23.common.components.inventory.PickUpComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.TrajectoryComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.sound.FootstepSoundComponent;
import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
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

public class Worker{
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = { 6, 6, 6, 6, 6, 6 };
        final int[] order = { 0, 1, 2, 3, 4, 5 };
        Image img = new Image(Objects.requireNonNull(Worker.class.getResourceAsStream("/charactersprites/Worker.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 6, img.getHeight() / 6), order);
    }

    public static Entity create() {
        Entity worker = new Entity(IEntitySPI.Type.WORKER);
        var position = new PositionComponent();
        worker.add(position);

        var direction = new DirectionComponent();
        worker.add(direction);

        var velocity = new VelocityComponent();
        worker.add(velocity);

        var oval = new OvalShape((21d * 0.5) / 64, (4d * 0.5) / 64);
        var collision = new CollisionComponent(oval);
        worker.add(collision);

        var rect = new AABBShape(0.6, 0.85);
        var hitBox = new HitBoxComponent(rect, new Vector2D(0, 0.4));
        worker.add(hitBox);

        var deathAnimation = Engine.getEntitySPI(IEntitySPI.Type.DEATH_ANIMATION);
        var health = new HealthComponent(100);
        health.onDeath = node -> {
            assert deathAnimation != null;
            Engine.addEntity(deathAnimation.create(Engine.getEntity(node.getEntityID())));
        };
        worker.add(health);

        health.onHurt = node -> {
            Entity e = new Entity(null);
            e.add(new SoundComponent(SoundEffect.KNIGHT_HURT, position.position, 0));
            Engine.addEntity(e);
        };

        worker.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        worker.add(new AnimationComponent());

        worker.get(VelocityComponent.class).speed = 1.5;

        worker.get(HealthComponent.class).invincible = true;

        var spriteComponent = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -127d / 192));
        worker.add(spriteComponent);

        IEntitySPI damageSPI = Engine.getEntitySPI(IEntitySPI.Type.DAMAGE);
        var actions = new ActionSetComponent();
        var action1 = new Action(Action.Directionality.BI);
        action1.animationIndex = 2;
        action1.duration = 600;
        action1.delay = 300;
        action1.onDispatch = node -> {
            if (damageSPI == null) return;
            Entity damageEntity = damageSPI.create(Engine.getEntity(node.getEntityID()));
            Entity sound = new Entity(null);
            sound.add(new SoundComponent(SoundEffect.WOOSH1, position.position));
            Engine.addEntity(sound);
            Engine.addEntity(damageEntity);
        };
        action1.strength = 1;
        actions.actions.add(action1);
        worker.add(actions);

        worker.add(new LayerComponent(LayerComponent.Layer.NPC)); // Needs new layer for the worker

        worker.add(new FootstepSoundComponent(SoundEffect.FOOTSTEP_PLAYER, Set.of(1, 4))); // Same sound as knight is
                                                                                           // fine.

        var inventory = worker.add(new InventoryComponent(3, IEntitySPI.Type.STONE));

        var pickup = worker.add(new PickUpComponent());
        pickup.range = 1.0;
        var workerComponent = worker.add(new WorkerComponent());

        var spawnStoneSPI = Engine.getEntitySPI(IEntitySPI.Type.SPAWN_STONE);
        var stoneSPI = Engine.getEntitySPI(IEntitySPI.Type.STONE);
        var action2 = new Action(Action.Directionality.MONO);
        action2.animationIndex = 4;
        action2.duration = 400;
        action2.delay = 200;
        action2.onDispatch = node -> {
            if (spawnStoneSPI == null || stoneSPI == null) return;
            var amount = inventory.amounts.get(IEntitySPI.Type.STONE);
            if (amount < 1) return;
            inventory.amounts.put(IEntitySPI.Type.STONE, amount - 1);


            var stone = spawnStoneSPI.create(worker);
            var end = workerComponent.home.get(PositionComponent.class).position;
            Dispatch onEnd = dNode -> {
                var stoneItem = stoneSPI.create(null);
                stoneItem.get(PositionComponent.class).position.set(end);
                Engine.addEntity(stoneItem);
            };
           stone.add(new TrajectoryComponent(position.position, end, 1, 3 ,onEnd));

            Entity sound = new Entity(null);
            sound.add(new SoundComponent(SoundEffect.STONE_DROP, position.position));
            Engine.addEntity(sound);
            Engine.addEntity(stone);
        };
        actions.actions.add(action2);


        var attack = worker.add(new AttackComponent(2, 0.8));
        attack.speed = 0.75;

        var ai = worker.add(new AIComponent(
                AIComponent.Type.WORKER, // hvad worker selv er
                List.of(AIComponent.Type.STONE, AIComponent.Type.MINE), // hvad den leder efter
                AIComponent.Priority.CLOSEST // hvordan den vælger mål
        ));
        ai.range = 1000;
        worker.add(new PathFindingComponent());

        return worker;

    }
}

package dk.sdu.petni23.enemy;

import dk.sdu.petni23.character.Character;
import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.ai.PathFindingComponent;
import dk.sdu.petni23.common.components.damage.AttackComponent;
import dk.sdu.petni23.common.components.health.HealthBarComponent;
import dk.sdu.petni23.common.components.items.LootComponent;
import dk.sdu.petni23.common.components.actions.Action;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.gameflow.SpawnComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.score.ScoreComponent;
import dk.sdu.petni23.common.sound.SoundEffect;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Objects;

public class TorchGoblin implements IEntitySPI
{
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {7,6,6,6,6};
        final int[] order = {0,1,4,2,3};
        Image img = new Image(Objects.requireNonNull(TorchGoblin.class.getResourceAsStream("/enemysprites/Goblin.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 7, img.getHeight() / 5), order);
    }

    public static Entity create(Vector2D pos)
    {
        Entity goblin = Character.create(pos, 30, SoundEffect.GOBLIN_HURT, IEntitySPI.Type.TORCH_GOBLIN);

        goblin.get(VelocityComponent.class).speed = 2.5;

        var spriteComponent = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -127d / 192));
        goblin.add(spriteComponent);

        IEntitySPI damageSPI = Engine.getEntitySPI(IEntitySPI.Type.DAMAGE);
        var attack = new Action(Action.Directionality.QUAD);
        attack.animationIndex = 2;
        attack.duration = 600;
        attack.delay = 300;
        attack.onDispatch = node -> {
            assert damageSPI != null;
            Entity damageEntity = damageSPI.create(Engine.getEntity(node.getEntityID()));
            //damageEntity.add(new SoundComponent("woosh1"));
            Engine.addEntity(damageEntity);
        };
        attack.strength = 1;
        var actionSet = new ActionSetComponent();
        actionSet.actions.add(attack);
        goblin.add(actionSet);
        

        var attackComponent = goblin.add(new AttackComponent(2, 0.6));
        //attackComponent.speed = 2;


        goblin.add(new LayerComponent(LayerComponent.Layer.ENEMY));
        var goldSPI = Engine.getEntitySPI(Type.SPAWN_GOLD);
        var loot = goblin.add(new LootComponent(node -> {
            if (goldSPI != null) {
                Engine.addEntity(goldSPI.create(Engine.getEntity(node.getEntityID())));
            }
        }));
        loot.minDrop = 2;
        loot.maxDrop = 5;
        goblin.add(new HealthBarComponent(40, 5, Color.RED, 1.3));
        goblin.add(new ScoreComponent(125)); // 125 points for melee goblin

        goblin.add(new AIComponent(AIComponent.Type.CHARACTER, Arrays.asList(AIComponent.Type.TOWER, AIComponent.Type.CHARACTER, AIComponent.Type.NEXUS), AIComponent.Priority.CLOSEST));
        goblin.add(new PathFindingComponent());
        goblin.add(new SpawnComponent(false,pos,false));
        return goblin;
    }

    @Override
    public Entity create(Entity parent) {
        return create(Vector2D.ZERO);
    }

    @Override
    public Type getType() {
        return Type.TORCH_GOBLIN;
    }
}

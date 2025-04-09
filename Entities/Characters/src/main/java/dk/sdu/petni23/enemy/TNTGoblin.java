package dk.sdu.petni23.enemy;

import dk.sdu.petni23.character.Character;
import dk.sdu.petni23.common.components.AIComponent;
import dk.sdu.petni23.common.components.actions.Action;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.damage.ThrowComponent;
import dk.sdu.petni23.common.components.health.HealthBarComponent;
import dk.sdu.petni23.common.components.items.LootComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Objects;

public class TNTGoblin
{
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {6,6,7};
        Image img = new Image(Objects.requireNonNull(TorchGoblin.class.getResourceAsStream("/enemysprites/TNTGoblin.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 7, img.getHeight() / 3));
    }

    public static Entity create(Vector2D pos) {
        Entity goblin = Character.create(pos, 20, "goblin_hurt1");

        goblin.get(VelocityComponent.class).speed = 2.3;

        var spriteComponent = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -127d / 192));
        goblin.add(spriteComponent);

        goblin.add(new LayerComponent(LayerComponent.Layer.ENEMY));
        var goldSPI = Engine.getEntitySPI(IEntitySPI.Type.GOLD);
        var loot = goblin.add(new LootComponent(node -> {
            if (goldSPI != null) {
                Engine.addEntity(goldSPI.create(Engine.getEntity(node.getEntityID())));
            }
        }));
        loot.maxDrop = 3;

        goblin.add(new ThrowComponent(5));

        IEntitySPI dynamiteSPI = Engine.getEntitySPI(IEntitySPI.Type.DYNAMITE);
        var actions = goblin.add(new ActionSetComponent());
        var attack = new Action(Action.Directionality.BI);
        actions.actions.add(attack);
        attack.animationIndex = 2;
        attack.duration = 600;
        attack.delay = 300;
        attack.onDispatch = node -> {
            assert dynamiteSPI != null;
            Engine.addEntity(dynamiteSPI.create(Engine.getEntity(node.getEntityID())));
        };
        attack.strength = 1;
        goblin.add(new HealthBarComponent(40, 5, Color.RED));

        goblin.add(new AIComponent(AIComponent.Type.CHARACTER, Arrays.asList(AIComponent.Type.CHARACTER, AIComponent.Type.TOWER, AIComponent.Type.NEXUS), AIComponent.Priority.CLOSEST));

        return goblin;
    }
}

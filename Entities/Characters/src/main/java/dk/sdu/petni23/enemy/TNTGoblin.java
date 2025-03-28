package dk.sdu.petni23.enemy;

import dk.sdu.petni23.character.Character;
import dk.sdu.petni23.common.components.items.LootComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.life.LayerComponent;
import dk.sdu.petni23.common.components.movement.SpeedComponent;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import javafx.scene.image.Image;

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
        Entity goblin = Character.create(pos, 20);

        var speed = new SpeedComponent();
        speed.speed = 2.3;
        goblin.add(speed);

        var spriteComponent = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -127d / 192));
        goblin.add(spriteComponent);

        goblin.add(new LayerComponent(LayerComponent.Layer.ENEMY));
        var goldSPI = Engine.getEntitySPI(IEntitySPI.Type.GOLD);
        var loot = goblin.add(new LootComponent(node -> {
            if (goldSPI != null) {
                Engine.addEntity(goldSPI.create(node));
            }
        }));
        loot.maxDrop = 3;

        return goblin;
    }
}

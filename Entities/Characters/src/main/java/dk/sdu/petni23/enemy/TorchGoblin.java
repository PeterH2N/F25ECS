package dk.sdu.petni23.enemy;

import dk.sdu.petni23.character.Character;
import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.hp.HealthComponent;
import dk.sdu.petni23.common.components.hp.LayerComponent;
import dk.sdu.petni23.common.components.movement.SpeedComponent;
import dk.sdu.petni23.common.components.SpriteComponent;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import javafx.scene.image.Image;

import java.util.Objects;

public class TorchGoblin
{
    private static final SpriteSheet spriteSheet = new SpriteSheet();

    static {
        final int[] numFrames = {7,6,6,6,6};
        Image img = new Image(Objects.requireNonNull(TorchGoblin.class.getResourceAsStream("/enemysprites/Goblin.png")));
        spriteSheet.init(img, numFrames);
    }

    public static Entity create()
    {
        double x = Math.random() * GameData.worldSize - (double) GameData.worldSize / 2;
        double y = Math.random() * GameData.worldSize - (double) GameData.worldSize / 2;
        Entity goblin = Character.create(new Vector2D(x, y));

        var speed = new SpeedComponent();
        speed.speed = 2.5;
        goblin.add(speed);

        var spriteComponent = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -127d / 192));
        goblin.add(spriteComponent);

        goblin.add(new LayerComponent(LayerComponent.Layer.ENEMY));

        var health = new HealthComponent();
        health.setMaxHealth(30);
        goblin.add(health);

        return goblin;
    }
}

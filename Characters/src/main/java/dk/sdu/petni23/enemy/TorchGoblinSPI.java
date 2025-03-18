package dk.sdu.petni23.enemy;

import dk.sdu.petni23.character.Character;
import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.BodyComponent;
import dk.sdu.petni23.common.components.ControlComponent;
import dk.sdu.petni23.common.components.SpeedComponent;
import dk.sdu.petni23.common.components.SpriteComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.player.PlayerSPI;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Objects;

public class TorchGoblinSPI implements IEntitySPI
{
    SpriteSheet spriteSheet = new SpriteSheet();

    public TorchGoblinSPI() {
        final int[] numFrames = {7,6,6,6,6};
        Image img = new Image(Objects.requireNonNull(TorchGoblinSPI.class.getResourceAsStream("/enemysprites/Goblin.png")));
        spriteSheet.init(img, numFrames);
    }
    @Override
    public Entity create()
    {
        double x = Math.random() * GameData.worldSize - (double) GameData.worldSize / 2;
        double y = Math.random() * GameData.worldSize - (double) GameData.worldSize / 2;
        Entity goblin = Character.create(new Vector2D(x, y));

        var speed = new SpeedComponent();
        speed.speed = 2.5;
        goblin.add(speed);

        var aabb = new AABBShape();
        aabb.width = 21d / 64;
        aabb.height = 4d / 64;
        var body = new BodyComponent();
        body.setShape(aabb);
        goblin.add(body);

        var spriteComponent = new SpriteComponent(this.spriteSheet, new Vector2D(-0.5, -127d / 192));
        goblin.add(spriteComponent);

        return goblin;
    }
}

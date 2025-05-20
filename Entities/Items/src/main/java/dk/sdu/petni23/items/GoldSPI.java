package dk.sdu.petni23.items;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.health.DurationComponent;
import dk.sdu.petni23.common.components.items.CurrencyComponent;
import dk.sdu.petni23.common.components.items.ItemComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.common.sound.SoundEffect;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;

import java.util.Objects;

public class GoldSPI implements IEntitySPI {

    private static final SpriteSheet goldSprite;

    private final static Vector2D origin = new Vector2D(-0.5, -0.72);

    static {
        int[] numFrames = { 1 };
        Image img = new Image(Objects.requireNonNull(SpawnGoldSPI.class.getResourceAsStream("/itemsprites/Gold_Idle.png")));
        goldSprite = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }
    @Override
    public Entity create(Entity parent) {
        return gold(Vector2D.ZERO);
    }

    static Entity gold(Vector2D pos) {
        Entity gold = new Entity(Type.GOLD);
        gold.add(new ItemComponent(IEntitySPI.Type.GOLD));
        var positionComponent = new PositionComponent();
        positionComponent.position.set(pos);
        gold.add(positionComponent);
        SpriteComponent spriteComponent = new SpriteComponent(goldSprite, origin);
        gold.add(spriteComponent);
        gold.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        gold.add(new VelocityComponent());
        gold.add(new CurrencyComponent());

        ItemComponent item = new ItemComponent(IEntitySPI.Type.GOLD);
        item.onPickup = node -> {
            Entity e = new Entity(null);
            e.add(new SoundComponent(SoundEffect.COIN_PICKUP, positionComponent.position, 150));
            e.add(new DurationComponent(200, GameData.getCurrentMillis()));
            Engine.addEntity(e);
        };
        gold.add(item);

        return gold;
    }

    @Override
    public Type getType() {
        return Type.GOLD;
    }
}

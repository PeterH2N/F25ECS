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

public class WoodSPI implements IEntitySPI {
    private static final SpriteSheet woodSprite;
    private final static Vector2D origin = new Vector2D(-0.5, -0.72);

    static {
        int[] numFrames = { 1 };
        Image img = new Image(Objects.requireNonNull(SpawnWoodSPI.class.getResourceAsStream("/itemsprites/Wood_Idle.png")));
        woodSprite = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }
    @Override
    public Entity create(Entity parent) {
        return wood(Vector2D.ZERO);
    }

    static Entity wood(Vector2D pos) {
        Entity wood = new Entity(Type.WOOD);
        wood.add(new ItemComponent(IEntitySPI.Type.WOOD));
        var positionComponent = new PositionComponent();
        positionComponent.position.set(pos);
        wood.add(positionComponent);
        SpriteComponent spriteComponent = new SpriteComponent(woodSprite, origin);
        wood.add(spriteComponent);
        wood.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        wood.add(new VelocityComponent());
        wood.add(new CurrencyComponent());

        ItemComponent item = new ItemComponent(IEntitySPI.Type.WOOD);
        item.onPickup = node -> {
            Entity soundEntity = new Entity(null);
            soundEntity.add(new SoundComponent(SoundEffect.WOOD_PICKUP, positionComponent.position, 150));
            soundEntity.add(new DurationComponent(200, GameData.getCurrentMillis()));
            Engine.addEntity(soundEntity);
        };
        wood.add(item);

        return wood;
    }

    @Override
    public Type getType() {
        return Type.WOOD;
    }
}

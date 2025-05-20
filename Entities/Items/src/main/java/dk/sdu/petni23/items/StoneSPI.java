package dk.sdu.petni23.items;

import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
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

public class StoneSPI implements IEntitySPI {

    private static final SpriteSheet stoneSprite;

    private final static Vector2D origin = new Vector2D(-0.5, -0.72);

    static {
        int[] numFrames = { 1 };
        Image img = new Image(Objects.requireNonNull(SpawnStoneSPI.class.getResourceAsStream("/itemsprites/Stone_Idle.png")));
        stoneSprite = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }
    @Override
    public Entity create(Entity parent) {
        return stone(Vector2D.ZERO);
    }

    static Entity stone(Vector2D pos) {
        Entity stone = new Entity(Type.STONE);
        stone.add(new ItemComponent(IEntitySPI.Type.STONE));
        var positionComponent = new PositionComponent();
        positionComponent.position.set(pos);
        stone.add(positionComponent);
        SpriteComponent spriteComponent = new SpriteComponent(stoneSprite, origin);
        stone.add(spriteComponent);
        stone.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        stone.add(new VelocityComponent());
        stone.add(new CurrencyComponent());
        stone.add(new LayerComponent(LayerComponent.Layer.NPC_TARGET));

        ItemComponent item = new ItemComponent(IEntitySPI.Type.STONE);
        item.onPickup = node -> {
            Entity soundEntity = new Entity(null);
            soundEntity.add(new SoundComponent(SoundEffect.STONE_PICKUP, positionComponent.position, 150));
            Engine.addEntity(soundEntity);
        };
        stone.add(item);
        stone.add(new AIComponent(AIComponent.Type.STONE, null, null));

        return stone;
    }


    @Override
    public Type getType() {
        return Type.STONE;
    }
}

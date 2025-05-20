package dk.sdu.petni23.items;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.health.DurationComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
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

public class MeatSPI implements IEntitySPI {

    private static final SpriteSheet meatSprite;

    private final static Vector2D origin = new Vector2D(-0.5, -0.72);

    static {
        int[] numFrames = { 1 };
        Image img = new Image(Objects.requireNonNull(SpawnMeatSPI.class.getResourceAsStream("/itemsprites/Meat_Idle.png")));
        meatSprite = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }
    @Override
    public Entity create(Entity parent) {
        return meat(Vector2D.ZERO);
    }

    static Entity meat(Vector2D pos) {
        Entity meat = new Entity(Type.MEAT);
        meat.add(new ItemComponent(IEntitySPI.Type.MEAT));
        var positionComponent = new PositionComponent();
        positionComponent.position.set(pos);
        meat.add(positionComponent);
        SpriteComponent spriteComponent = new SpriteComponent(meatSprite, origin);
        meat.add(spriteComponent);
        meat.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        meat.add(new VelocityComponent());
        meat.add(new CurrencyComponent());

        ItemComponent item = new ItemComponent(IEntitySPI.Type.MEAT);
        item.onPickup = node -> {
            Entity soundEntity = new Entity(null);
            soundEntity.add(new SoundComponent(SoundEffect.MEAT_PICKUP, positionComponent.position, 150));
            soundEntity.add(new DurationComponent(200, GameData.getCurrentMillis()));
            Engine.addEntity(soundEntity);

            HealthComponent health = node.getComponent(HealthComponent.class);
            if (health != null) {
                health.heal(20);
            }
        };
        meat.add(item);

        return meat;
    }

    @Override
    public Type getType() {
        return Type.MEAT;
    }
}

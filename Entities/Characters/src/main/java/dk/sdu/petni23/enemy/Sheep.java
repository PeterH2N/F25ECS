package dk.sdu.petni23.enemy;

import dk.sdu.petni23.character.Character;
import dk.sdu.petni23.common.components.items.LootComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.sound.FootstepSoundComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.sound.SoundEffect;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Set;

public class Sheep implements IEntitySPI {
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = { 8, 6 };
        final int[] order = { 0, 1 };
        Image img = new Image(Objects.requireNonNull(Sheep.class.getResourceAsStream("/enemysprites/Sheep.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 8, img.getHeight() / 2), order);

    }

    public static Entity create(Vector2D pos) {
        Entity sheep = Character.create(pos, 20, SoundEffect.SHEEP_HURT, IEntitySPI.Type.SHEEP);

        sheep.get(VelocityComponent.class).speed = 2.3;

        var spriteComponent = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -127d / 192));
        sheep.add(spriteComponent);

        sheep.add(new LayerComponent(LayerComponent.Layer.ENEMY));
        var meatSPI = Engine.getEntitySPI(Type.SPAWN_MEAT);
        var loot = sheep.add(new LootComponent(node -> {
            if (meatSPI != null) {
                Engine.addEntity(meatSPI.create(Engine.getEntity(node.getEntityID())));
            }
        }));
        loot.maxDrop = 3;

        sheep.add(new FootstepSoundComponent(SoundEffect.SHEEP_WALK, Set.of(0)));
        //sheep.add(new HealthBarComponent(40, 5, Color.DARKGREEN));

        return sheep;
    }

    @Override
    public Entity create(Entity parent) {
        return create(Vector2D.ZERO);
    }

    @Override
    public Type getType() {
        return Type.SHEEP;
    }
}

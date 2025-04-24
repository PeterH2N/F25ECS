package dk.sdu.petni23.items;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.items.CurrencyComponent;
import dk.sdu.petni23.common.components.items.ItemComponent;
import dk.sdu.petni23.common.components.health.DurationComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.common.sound.SoundEffect;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.image.Image;

import java.util.Objects;

public class GoldSPI implements IEntitySPI {
    private static final SpriteSheet goldSprite;
    private static final SpriteSheet spawnGoldSprite;
    private final static double spawnRadius = 1.5;
    private final static Vector2D origin = new Vector2D(-0.5, -0.72);

    static {
        int[] numFrames = { 1 };
        Image img = new Image(Objects.requireNonNull(GoldSPI.class.getResourceAsStream("/itemsprites/Gold_Idle.png")));
        goldSprite = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
        numFrames = new int[] { 7 };
        img = new Image(Objects.requireNonNull(GoldSPI.class.getResourceAsStream("/itemsprites/Gold_Spawn.png")));
        spawnGoldSprite = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 7, img.getHeight()));
    }

    @Override
    public Entity create(Entity parent) {
        double radius = Math.max(0.7, Math.random() * spawnRadius);
        double dirX = Math.random() * 2 - 1;
        double dirY = Math.random() * 2 - 1;
        Vector2D p = new Vector2D(dirX, dirY).getNormalized().getMultiplied(radius);
        var parentPositionComponent = parent.get(PositionComponent.class);
        assert parentPositionComponent != null;
        Vector2D pos = parentPositionComponent.position.getAdded(p);
        Entity sound = new Entity(null);
        sound.add(new SoundComponent(SoundEffect.COIN_BAG_DROP, pos, 50));
        Engine.addEntity(sound);
        return spawnGold(pos);
    }

    @Override
    public IEntitySPI.Type getType() {
        return IEntitySPI.Type.GOLD;
    }

    Entity gold(Vector2D pos) {
        Entity gold = new Entity(null);
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

    Entity spawnGold(Vector2D pos) {
        Entity spawn = new Entity(null);
        var positionComponent = new PositionComponent();
        positionComponent.position.set(pos);
        spawn.add(positionComponent);
        SpriteComponent spriteComponent = new SpriteComponent(spawnGoldSprite, origin);
        spawn.add(spriteComponent);
        spawn.add(new AnimationComponent());
        spawn.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        var duration = spawn.add(new DurationComponent(700, GameData.getCurrentMillis()));
        duration.onDeath = node -> Engine.addEntity(gold(pos));
        return spawn;
    }
}

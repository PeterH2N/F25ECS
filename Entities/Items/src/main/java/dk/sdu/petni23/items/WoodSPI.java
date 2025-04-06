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
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.image.Image;

import java.util.Objects;

public class WoodSPI implements IEntitySPI {
    private static final SpriteSheet woodSprite;
    private static final SpriteSheet spawnWoodSprite;
    private final static double spawnRadius = 1.5;
    private final static Vector2D origin = new Vector2D(-0.5, -0.72);

    static {
        int[] numFrames = { 1 };
        Image img = new Image(Objects.requireNonNull(WoodSPI.class.getResourceAsStream("/itemsprites/Wood_Idle.png")));
        woodSprite = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
        numFrames = new int[] { 7 };
        img = new Image(Objects.requireNonNull(WoodSPI.class.getResourceAsStream("/itemsprites/Wood_Spawn.png")));
        spawnWoodSprite = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 7, img.getHeight()));
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

        return spawnWood(pos);
    }

    @Override
    public IEntitySPI.Type getType() {
        return IEntitySPI.Type.WOOD;
    }

    Entity wood(Vector2D pos) {
        Entity wood = new Entity();
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
            Entity soundEntity = new Entity();
            soundEntity.add(new SoundComponent("wood_pickup1", 150, 0.5));
            soundEntity.add(new DurationComponent(200, GameData.getCurrentMillis()));
            Engine.addEntity(soundEntity);
        };
        wood.add(item);

        return wood;
    }

    Entity spawnWood(Vector2D pos) {
        Entity spawn = new Entity();
        var positionComponent = new PositionComponent();
        positionComponent.position.set(pos);
        spawn.add(positionComponent);
        SpriteComponent spriteComponent = new SpriteComponent(spawnWoodSprite, origin);
        spawn.add(spriteComponent);
        spawn.add(new AnimationComponent());
        spawn.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

        var duration = spawn.add(new DurationComponent(700, GameData.getCurrentMillis()));
        duration.onDeath = node -> Engine.addEntity(wood(pos));
        return spawn;
    }
}

package dk.sdu.petni23.items;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.items.CurrencyComponent;
import dk.sdu.petni23.common.components.items.ItemComponent;
import dk.sdu.petni23.common.components.health.DurationComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.TrajectoryComponent;
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
import java.util.Vector;

public class StoneSPI implements IEntitySPI {
    private static final SpriteSheet stoneSprite;
    private static final SpriteSheet spawnStoneSprite;
    private final static double spawnRadius = 2.5;
    private final static Vector2D origin = new Vector2D(-0.5, -0.72);

    static {
        int[] numFrames = { 1 };
        Image img = new Image(Objects.requireNonNull(StoneSPI.class.getResourceAsStream("/itemsprites/Stone_Idle.png")));
        stoneSprite = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
        numFrames = new int[] { 1 };
        img = new Image(Objects.requireNonNull(StoneSPI.class.getResourceAsStream("/itemsprites/Stone_Spawn.png")));
        spawnStoneSprite = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }

    @Override
    public Entity create(Entity parent) {
        double radius = Math.max(1, Math.random() * spawnRadius);
        double dirX = Math.random() * 2 - 1;
        double dirY = Math.random() * 2 - 1;
        Vector2D p = new Vector2D(dirX, dirY).getNormalized().getMultiplied(radius);
        var parentPositionComponent = parent.get(PositionComponent.class);
        assert parentPositionComponent != null;
        Vector2D start = parentPositionComponent.position;
        Vector2D end = start.getAdded(p);

        return spawnStone(start, end);
    }

    @Override
    public IEntitySPI.Type getType() {
        return IEntitySPI.Type.STONE;
    }

    Entity stone(Vector2D pos) {
        Entity stone = new Entity();
        stone.add(new ItemComponent(IEntitySPI.Type.STONE));
        var positionComponent = new PositionComponent();
        positionComponent.position.set(pos);
        stone.add(positionComponent);
        SpriteComponent spriteComponent = new SpriteComponent(stoneSprite, origin);
        stone.add(spriteComponent);
        stone.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        stone.add(new VelocityComponent());
        stone.add(new CurrencyComponent());

        ItemComponent item = new ItemComponent(IEntitySPI.Type.STONE);
        item.onPickup = node -> {
            Entity soundEntity = new Entity();
            soundEntity.add(new SoundComponent("stone_pickup1", 150, 0.5));
            soundEntity.add(new DurationComponent(200, GameData.getCurrentMillis()));
            Engine.addEntity(soundEntity);
        };
        stone.add(item);

        return stone;
    }

    public Entity spawnStone(Vector2D start, Vector2D end) {
        Entity spawn = new Entity();
        var positionComponent = new PositionComponent();
        positionComponent.position.set(start);
        spawn.add(positionComponent);
        SpriteComponent spriteComponent = new SpriteComponent(spawnStoneSprite, origin);
        spawn.add(spriteComponent);
        spawn.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        double dist = end.getSubtracted(start).getLength();
        spawn.add(new TrajectoryComponent(start, end, 1, 3 ,node -> Engine.addEntity(stone(end))));

        spawn.add(new SoundComponent("stone_drop1", 50, 0.8));
        return spawn;
    }
}

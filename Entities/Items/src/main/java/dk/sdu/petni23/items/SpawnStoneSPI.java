package dk.sdu.petni23.items;

import dk.sdu.petni23.common.components.items.CurrencyComponent;
import dk.sdu.petni23.common.components.items.ItemComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.TrajectoryComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
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

public class SpawnStoneSPI implements IEntitySPI {
    private static final SpriteSheet spawnStoneSprite;
    private final static double spawnRadius = 2.5;
    private final static Vector2D origin = new Vector2D(-0.5, -0.72);

    static {

        var numFrames = new int[] { 1 };
        var img = new Image(Objects.requireNonNull(SpawnStoneSPI.class.getResourceAsStream("/itemsprites/Stone_Spawn.png")));
        spawnStoneSprite = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
    }

    @Override
    public Entity create(Entity parent) {
        double radius = Math.max(1, Math.random() * spawnRadius);
        double dirX = Math.random() * 2 - 1;
        double dirY = Math.random() * 2 - 1;
        Vector2D p = new Vector2D(dirX, dirY).getNormalized().getMultiplied(radius);
        Vector2D start = Vector2D.ZERO;
        if (parent != null) {
            var parentPositionComponent = parent.get(PositionComponent.class);
            start = parentPositionComponent.position;
        }
        Vector2D end = start.getAdded(p);
        Entity sound = new Entity(null);
        sound.add(new SoundComponent(SoundEffect.STONE_DROP, start, 50));
        Engine.addEntity(sound);

        return spawnStone(start, end);
    }

    @Override
    public IEntitySPI.Type getType() {
        return Type.SPAWN_STONE;
    }



    public Entity spawnStone(Vector2D start, Vector2D end) {
        Entity spawn = new Entity(Type.SPAWN_STONE);
        var positionComponent = new PositionComponent();
        positionComponent.position.set(start);
        spawn.add(positionComponent);
        SpriteComponent spriteComponent = new SpriteComponent(spawnStoneSprite, origin);
        spawn.add(spriteComponent);
        spawn.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        spawn.add(new TrajectoryComponent(start, end, 1, 3 ,node -> Engine.addEntity(StoneSPI.stone(end))));


        return spawn;
    }
}

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

public class SpawnWoodSPI implements IEntitySPI {

    private static final SpriteSheet spawnWoodSprite;
    private final static double spawnRadius = 1.5;
    private final static Vector2D origin = new Vector2D(-0.5, -0.72);

    static {
        var numFrames = new int[] { 7 };
        var img = new Image(Objects.requireNonNull(SpawnWoodSPI.class.getResourceAsStream("/itemsprites/Wood_Spawn.png")));
        spawnWoodSprite = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 7, img.getHeight()));
    }

    @Override
    public Entity create(Entity parent) {
        double radius = Math.max(0.7, Math.random() * spawnRadius);
        double dirX = Math.random() * 2 - 1;
        double dirY = Math.random() * 2 - 1;
        Vector2D p = new Vector2D(dirX, dirY).getNormalized().getMultiplied(radius);
        Vector2D pos = Vector2D.ZERO;
        if (parent != null) {
            var parentPositionComponent = parent.get(PositionComponent.class);
            pos = parentPositionComponent.position.getAdded(p);
        }

        return spawnWood(pos);
    }

    @Override
    public IEntitySPI.Type getType() {
        return Type.SPAWN_WOOD;
    }



    Entity spawnWood(Vector2D pos) {
        Entity spawn = new Entity(Type.SPAWN_WOOD);
        var positionComponent = new PositionComponent();
        positionComponent.position.set(pos);
        spawn.add(positionComponent);
        SpriteComponent spriteComponent = new SpriteComponent(spawnWoodSprite, origin);
        spawn.add(spriteComponent);
        spawn.add(new AnimationComponent());
        spawn.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

        var duration = spawn.add(new DurationComponent(700, GameData.getCurrentMillis()));
        duration.onDeath = node -> Engine.addEntity(WoodSPI.wood(pos));
        return spawn;
    }
}

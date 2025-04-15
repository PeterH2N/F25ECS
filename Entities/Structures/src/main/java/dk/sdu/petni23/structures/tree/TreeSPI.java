package dk.sdu.petni23.structures.tree;

import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.health.DurationComponent;
import dk.sdu.petni23.common.components.sound.SoundComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.items.LootComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.sound.SoundEffect;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;

import java.util.Objects;

public class TreeSPI implements IEntitySPI
{
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = { 4, 2, 1 };
        Image img = new Image(Objects.requireNonNull(TreeSPI.class.getResourceAsStream("/structuresprites/Tree.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 4, img.getHeight() / 3));
    }

    public static Entity createTree(Vector2D pos) {
        Entity tree = new Entity();

        var position = new PositionComponent();
        position.position.set(pos);
        tree.add(position);

        var sprite = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -0.875));
        tree.add(sprite);

        tree.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

        var aabb = new AABBShape((24d) / 64, (6d) / 64);
        var collision = new CollisionComponent(aabb);
        tree.add(collision);

        var health = new HealthComponent(20);
        health.onDeath = node -> Engine.addEntity(createStump(tree.get(PositionComponent.class).position));
        health.onHurt = node -> {
            Entity e = new Entity();
            e.add(new SoundComponent(SoundEffect.TREE_HIT, position.position, 150));
            Engine.addEntity(e);
        };

        var woodSPI = Engine.getEntitySPI(IEntitySPI.Type.WOOD);
        var loot = tree.add(new LootComponent(node -> {
            if (woodSPI != null) {
                Entity sound = new Entity();
                sound.add(new SoundComponent(SoundEffect.WOOD_DROP, position.position, 50));
                Engine.addEntity(sound);
                Engine.addEntity(woodSPI.create(Engine.getEntity(node.getEntityID())));
            }
        }));
        loot.maxDrop = 3;

        tree.add(health);

        var rect = new AABBShape(0.6, 0.7);
        var hitBox = new HitBoxComponent(rect, new Vector2D(0, 0.5));
        tree.add(hitBox);

        tree.add(new LayerComponent(LayerComponent.Layer.ALL));

        tree.add(new AnimationComponent());

        return tree;
    }

    public static Entity createStump(Vector2D pos) {
        Entity stump = new Entity();

        var position = new PositionComponent();
        position.position.set(pos);
        stump.add(position);

        var sprite = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -0.875), 0, 2);
        stump.add(sprite);

        stump.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

        var duration = new DurationComponent(GameData.random.nextInt(10000, 50000), GameData.getCurrentMillis()); // between 10 and 50 seconds
        duration.onDeath = node -> Engine.addEntity(createTree(pos));
        stump.add(duration);

        var aabb = new AABBShape((24d) / 64, (6d) / 64);
        var collision = new CollisionComponent(aabb);
        stump.add(collision);

        return stump;
    }

    @Override
    public Entity create(Entity parent)
    {
        return createTree(new Vector2D(0,0));
    }

    @Override
    public Type getType()
    {
        return Type.TREE;
    }
}

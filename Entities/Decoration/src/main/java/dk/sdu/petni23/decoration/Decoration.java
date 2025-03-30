package dk.sdu.petni23.decoration;

import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import javafx.scene.image.Image;

import java.util.Objects;

public class Decoration
{
    private static final int numSprites = 12;
    private static final SpriteSheet[] spriteSheets = new SpriteSheet[numSprites];
    private static final Vector2D[] origins;
    private static final Shape[] shapes;

    static {
        String templatePath = "/decosprites/";
        int[] numFrames = new int[]{1};
        for (int i = 0; i < numSprites; i++) {
            String path = templatePath + (i + 1) + ".png";
            Image img = new Image(Objects.requireNonNull(Decoration.class.getResourceAsStream(path)));
            spriteSheets[i] = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
        }

        origins = new Vector2D[]{
                new Vector2D(-0.5, -39 / 64.0),
                new Vector2D(-0.5, -43 / 64.0),
                new Vector2D(-0.5, -39 / 64.0),
                new Vector2D(-0.52, -33 / 64.0),
                new Vector2D(-0.5, -43 / 64.0),
                new Vector2D(-0.5, -49 / 64.0),
                new Vector2D(-0.5, -43 / 64.0),
                new Vector2D(-0.5, -42 / 64.0),
                new Vector2D(-0.5, -44 / 64.0),
                new Vector2D(-0.5, -47 / 64.0),
                new Vector2D(-0.5, -42 / 64.0),
                new Vector2D(-0.5, -44 / 64.0),
        };

        shapes = new Shape[numSprites];

        var shape3 = new OvalShape();
        shape3.a = (17d * 0.5) / 64;
        shape3.b = (3d * 0.5) / 36;
        shapes[3] = shape3;
    }

    public static Entity create(Vector2D pos)
    {
        // randomly create one of the 18 different types of decorations
        int t = (int) (Math.random() * numSprites);
        Entity decoration = new Entity();
        PositionComponent positionComponent = new PositionComponent();
        decoration.add(positionComponent);
        positionComponent.position.set(pos);
        decoration.add(new SpriteComponent(spriteSheets[t], origins[t]));
        decoration.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

        if (shapes[t] != null) {
            var collision = new CollisionComponent(shapes[t]);
            decoration.add(collision);
        }

        return decoration;
    }
}

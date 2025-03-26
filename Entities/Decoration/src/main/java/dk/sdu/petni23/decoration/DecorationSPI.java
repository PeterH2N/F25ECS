package dk.sdu.petni23.decoration;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.DisplayComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.SpriteComponent;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;
import javafx.scene.image.Image;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import java.util.Objects;

public class DecorationSPI implements IEntitySPI
{
    final int numSprites = 12;
    SpriteSheet[] spriteSheets = new SpriteSheet[numSprites];
    Vector2D[] origins;
    Shape[] shapes;

    public DecorationSPI() {
        String templatePath = "/decosprites/";
        int[] numFrames = new int[]{1};
        for (int i = 0; i < numSprites; i++) {
            String path = templatePath + (i + 1) + ".png";
            Image img = new Image(Objects.requireNonNull(DecorationSPI.class.getResourceAsStream(path)));
            spriteSheets[i] = new SpriteSheet();
            spriteSheets[i].init(img, numFrames, new Vector2D(img.getWidth(), img.getHeight()));
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
    @Override
    public Entity create(Node parent)
    {
        // randomly create one of the 18 different types of decorations
        int t = (int) (Math.random() * numSprites);
        Entity decoration = new Entity();
        PositionComponent positionComponent = new PositionComponent();
        decoration.add(positionComponent);
        double x = Math.random() * GameData.worldSize - (double) GameData.worldSize / 2;
        double y = Math.random() * GameData.worldSize - (double) GameData.worldSize / 2;
        positionComponent.position.set(x, y);
        decoration.add(new SpriteComponent(spriteSheets[t], origins[t]));
        decoration.add(new DisplayComponent(DisplayComponent.Order.FOREGROUND));

        if (shapes[t] != null) {
            var collision = new CollisionComponent(shapes[t]);
            decoration.add(collision);
        }

        return decoration;
    }

    @Override
    public Type getType()
    {
        return Type.ENVIRONMENT;
    }
}

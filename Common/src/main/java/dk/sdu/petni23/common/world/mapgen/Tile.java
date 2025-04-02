package dk.sdu.petni23.common.world.mapgen;

import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Tile
{
    public static final Map<Type, SpriteSheet> sheets = new HashMap<>();

    static {
        int[] numFrames = {4,4,4,4};
        Image img = new Image(Objects.requireNonNull(Tile.class.getResourceAsStream("/tilesprites/Ground.png")));
        sheets.put(Type.GRASS, new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 10, img.getHeight() / 4)));
        sheets.put(Type.SAND, new SpriteSheet(img, numFrames, 5, new Vector2D(img.getWidth() / 10, img.getHeight() / 4)));
    }
    public final Type type;
    public Placement placement;

    public Tile(Type type)
    {
        this.type = type;
    }

    public enum Type {
        WATER(0),
        SAND(1),
        GRASS(2),
        BRIDGE(3);

        private final int i;
        Type(int i) {
            this.i = i;
        }

        public int value() {
            return i;
        }
    }

    public enum Placement {
        EDGE_NW(0,0),
        EDGE_N(1,0),
        EDGE_NE(2,0),
        EDGE_NEW(3,0),
        EDGE_W(0,1),
        MIDDLE(1,1),
        EDGE_E(2,1),
        EDGE_EW(3,1),
        EDGE_SW(0,2),
        EDGE_S(1,2),
        EDGE_SE(2,2),
        EDGE_SEW(3,2),
        EDGE_NSW(0,3),
        EDGE_NS(1,3),
        EDGE_NSE(2,3),
        EDGE_NSEW(3,3);

        public final int x, y;

        Placement(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }
}

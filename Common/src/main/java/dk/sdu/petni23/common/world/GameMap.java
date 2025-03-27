package dk.sdu.petni23.common.world;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GameMap
{
    public Tile[][] tiles = new Tile[(int) GameData.worldSize][(int) GameData.worldSize];
    public Image[][] mapImages;
    public int imageSize;

    private final IEntitySPI foamSPI = Engine.getEntitySPI(IEntitySPI.Type.FOAM_ANIMATION);;

    public GameMap() {
        generateMap();
        refineMap();
        generateMapImage();
    }

    public Tile getTile(int x, int y) {
        // parameters are in world space
        int X = (x + GameData.worldSize / 2);
        int Y = (-y + GameData.worldSize / 2);

        if (X < 0 || X > GameData.worldSize - 1 || Y < 0 || Y > GameData.worldSize - 1)
            return null;

        return tiles[Y][X];
    }

    private void generateMap() {
        Vector2D center = new Vector2D((double) GameData.worldSize / 2, (double) GameData.worldSize / 2);
        for (int x = 0; x < GameData.worldSize; x++) {
            for (int y = 0; y < GameData.worldSize; y++) {

                Vector2D v = new Vector2D(x + 0.5, y + 0.5);
                double dist = center.distance(v);
                if (dist < (double)GameData.worldSize / 2 - 4)
                    tiles[y][x] = new Tile(Tile.Type.GRASS);
                else if (dist < (double)GameData.worldSize / 2 - 1)
                    tiles[y][x] = new Tile(Tile.Type.SAND);
                else
                    tiles[y][x] = new Tile(Tile.Type.WATER);
            }
        }
    }

    private void refineMap() {
        //List<Vector2D> colliders = new ArrayList<>();
        for (int x = (-GameData.worldSize / 2); x < GameData.worldSize / 2; x++) {
            for (int y = (GameData.worldSize / 2); y > -GameData.worldSize / 2; y--) {
                Tile tile = getTile(x, y);
                Tile nTile, sTile, eTile, wTile;
                sTile = getTile(x, y - 1);
                nTile = getTile(x, y + 1);
                eTile = getTile(x + 1, y);
                wTile = getTile(x - 1, y);
                if (tile.type != Tile.Type.WATER) {
                    // placement
                    boolean north = nTile == null || nTile.type.value() < tile.type.value();
                    boolean south = sTile == null || sTile.type.value() < tile.type.value();
                    boolean east = eTile == null || eTile.type.value() < tile.type.value();
                    boolean west = wTile == null || wTile.type.value() < tile.type.value();

                    // placement
                    int pX = 1;
                    int pY = 1;
                    if (north) pY = 0;
                    if (south) pY = 2;
                    if (north && south) pY = 3;

                    if (west) pX = 0;
                    if (east) pX = 2;
                    if (west && east) pX = 3;

                    for (Tile.Placement p : Tile.Placement.values()) {
                        if (pX == p.x && pY == p.y)
                            tile.placement = p;
                    }
                }

                if (coast(x, y))
                {
                    Engine.addEntity(BoxCollider(new Vector2D(x + 0.5, y - 0.5), 1, 1));
                }
                addFoam(x, y);
            }
        }
    }

    void addFoam(int x, int y) {
        Tile tile = getTile(x,y);
        if (tile.type == Tile.Type.WATER) return;
        // placement
        Tile pTile;
        // north
        pTile = getTile(x, y + 1);
        boolean north = (pTile != null && pTile.type == Tile.Type.WATER);

        // south
        pTile = getTile(x, y - 1);
        boolean south = (pTile != null && pTile.type == Tile.Type.WATER);

        // east
        pTile = getTile(x + 1, y);
        boolean east = (pTile != null && pTile.type == Tile.Type.WATER);

        // west
        pTile = getTile(x - 1, y);
        boolean west = (pTile != null && pTile.type == Tile.Type.WATER);

        // foam
        if (north || south || east || west) {
            Entity foam = foamSPI.create(null);
            foam.get(PositionComponent.class).position.set(x + 0.5, y - 0.5);
            Engine.addEntity(foam);
        }
    }

    boolean coast(int x, int y) {
        Tile tile = getTile(x, y);
        if (tile == null || tile.type != Tile.Type.WATER) return false;
        Tile nTile, sTile, eTile, wTile;
        sTile = getTile(x, y - 1);
        nTile = getTile(x, y + 1);
        eTile = getTile(x + 1, y);
        wTile = getTile(x - 1, y);

        boolean north = nTile == null || nTile.type == Tile.Type.WATER;
        boolean south = sTile == null || sTile.type == Tile.Type.WATER;
        boolean east = eTile == null || eTile.type == Tile.Type.WATER;
        boolean west = wTile == null || wTile.type == Tile.Type.WATER;
        return !(north && south && east && west);
    }

    private Entity BoxCollider(Vector2D pos, double width, double height) {
        Entity box = new Entity();
        var pc = new PositionComponent();
        pc.position.set(pos);
        box.add(pc);
        box.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        AABBShape aabb = new AABBShape();
        aabb.width = width;
        aabb.height = height;
        box.add(new CollisionComponent(aabb));

        return box;
    }

    private void generateMapImage() {
        int arraySize = (GameData.worldSize / 50) + ((GameData.worldSize % 50 == 0) ? 0 : 1);
        int numTiles = (GameData.worldSize / arraySize) + ((GameData.worldSize % arraySize == 0) ? 0 : 1);
        mapImages = new Image[arraySize][arraySize];

        imageSize = numTiles * 64;
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                Canvas canvas = new Canvas(imageSize, imageSize);
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setImageSmoothing(false);
                int startX = i * numTiles;
                int startY = j * numTiles;
                for (int x =  0; x <= (GameData.worldSize / arraySize) + 1; x++) {
                    for (int y = 0; y <= (GameData.worldSize / arraySize) + 1; y++) {
                        int mapX = x + startX;
                        int mapY = y + startY;
                        if (mapX >= GameData.worldSize || mapY >= GameData.worldSize) continue;

                        int imgX = x * 64;
                        int imgY = y * 64;
                        Tile tile = tiles[mapY][mapX];
                        for (Tile.Type type : Tile.Type.values()) {
                            if (type == tile.type) {
                                SpriteSheet sheet = Tile.sheets.get(tile.type);
                                if (sheet != null) {
                                    Image img = sheet.sheet[tile.placement.y][tile.placement.x];
                                    gc.drawImage(img, imgX, imgY, 64, 64);
                                }
                                break;
                            }
                            else {
                                SpriteSheet sheet = Tile.sheets.get(type);
                                if (sheet != null) {
                                    Image img = sheet.sheet[Tile.Placement.MIDDLE.y][Tile.Placement.MIDDLE.x];
                                    gc.drawImage(img, imgX, imgY, 64, 64);
                                }
                            }

                        }
                    }
                }
                var sp = new SnapshotParameters();
                sp.setFill(Color.TRANSPARENT);
                mapImages[j][i] = canvas.snapshot(sp, null);
            }

        }

    }

}

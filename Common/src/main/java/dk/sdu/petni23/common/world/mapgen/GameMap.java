package dk.sdu.petni23.common.world.mapgen;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.ai.PathFindingComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OBShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.SimplexNoise;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameMap
{
    private final List<Entity> mapEntities = new ArrayList<>();
    public Tile[][] tiles = new Tile[(int) GameData.worldSize][(int) GameData.worldSize];
    public Image[][] mapImages;
    public int imageSize;

    public final MapGenOptions genOptions;

    private final IEntitySPI foamSPI = Engine.getEntitySPI(IEntitySPI.Type.FOAM_ANIMATION);;

    public GameMap(MapGenOptions genOptions) {
        this.genOptions = genOptions;
        genMap();
    }

    public void genMap() {
        clearMapEntities();
        generateMap();
        refineMap();
        generateMapImage();
    }

    private void addEntity(Entity entity) {
        mapEntities.add(entity);
        Engine.addEntity(entity);
    }

    private void clearMapEntities() {
        mapEntities.forEach(Engine::removeEntity);
        mapEntities.clear();
    }

    public Tile getTile(int x, int y) {
        // parameters are in world space
        int X = (x + GameData.worldSize / 2);
        int Y = (-y + GameData.worldSize / 2);

        if (X < 0 || X > GameData.worldSize - 1 || Y < 0 || Y > GameData.worldSize - 1)
            return null;

        return tiles[Y][X];
    }

    private Vector2D toWorldSpace(int x, int y) {
        int X = x - GameData.worldSize / 2;
        int Y = -(y - GameData.worldSize / 2);

        if (X < - GameData.worldSize / 2 || X >= GameData.worldSize / 2 || Y < -GameData.worldSize / 2 || Y >= GameData.worldSize / 2)
            return null;

        return new Vector2D(X, Y);
    }

    private void generateMap() {
        Vector2D center = new Vector2D((double) GameData.worldSize / 2, (double) GameData.worldSize / 2);
        Tile[][] landMap = new Tile[GameData.worldSize][GameData.worldSize];
        double[][] noiseMap = new double[GameData.worldSize][GameData.worldSize];
        double[][] forestNoiseMap = new double[GameData.worldSize][GameData.worldSize];
        double[] shapeNoiseMap = new double[360];
        double[] coastNoiseMap = new double[360];
        int offset1 = genOptions.shapeOffset.get();
        int offset2 = genOptions.coastOffset.get();
        // init rotational noise
        for (int i = 0; i < 360; i++) {
            Vector2D pos = new Vector2D(10, 0).getRotatedTo(Math.toRadians(i));
            shapeNoiseMap[i] = SimplexNoise.noise(offset1 + pos.x * genOptions.islandShapeFrequency.get(), pos.y * genOptions.islandShapeFrequency.get());
            coastNoiseMap[i] = SimplexNoise.noise(offset2 + pos.x * genOptions.coastFrequency.get(), pos.y * genOptions.coastFrequency.get());
        }

        for (int x = 0; x < GameData.worldSize; x++) {
            int offset3 = genOptions.landOffset.get();
            int offset4 = genOptions.forestOffset.get();
            for (int y = 0; y < GameData.worldSize; y++) {
                double noise1 = (SimplexNoise.noise(offset3 + x * genOptions.landFrequency.get(), offset3 + y * genOptions.landFrequency.get()) + 1d) / 2d;
                noiseMap[y][x] = noise1;
                double forestNoise = (SimplexNoise.noise(offset4 + x * genOptions.forestFrequency.get(), offset4 + y * genOptions.forestFrequency.get()) + 1d) / 2d;
                forestNoiseMap[y][x] = forestNoise;


                Vector2D v = new Vector2D(x + 0.5, y + 0.5).getSubtracted(center);
                double dist = v.getLength();
                int angle = (int) v.angleDegrees() + 180;
                double shapeNoise = shapeNoiseMap[angle];
                double coastNoise = coastNoiseMap[angle];
                double shapeThreshold = Math.min(genOptions.islandRadius.get() + shapeNoise * genOptions.islandShapeAmplitude.get(), ((double) GameData.worldSize / 2) - 1);
                double beach = 3;
                double threshold = Math.min(shapeThreshold + coastNoise * genOptions.coastAmplitude.get(), ((double) GameData.worldSize / 2) - 1);
                if (dist < threshold - beach) landMap[y][x] = new Tile(Tile.Type.GRASS);
                else if (dist < threshold) landMap[y][x] = new Tile(Tile.Type.SAND);
                else landMap[y][x] = new Tile(Tile.Type.WATER);
            }
        }


        IEntitySPI treeSPI = Engine.getEntitySPI(IEntitySPI.Type.TREE);
        for (int x = 0; x < GameData.worldSize; x++){
            for (int y = 0; y < GameData.worldSize; y++) {
                if (landMap[y][x].type != Tile.Type.WATER) {
                    if (noiseMap[y][x] < genOptions.sandThreshold.get()) {
                        tiles[y][x] = new Tile(Tile.Type.WATER);
                    }
                    else if (noiseMap[y][x] < genOptions.grassThreshold.get()) {
                        tiles[y][x] = new Tile(Tile.Type.SAND);
                    }
                    else {
                        tiles[y][x] = new Tile(landMap[y][x].type);
                    }
                    // spawn trees
                    if (landMap[y][x].type == Tile.Type.GRASS && treeSPI != null && forestNoiseMap[y][x] > genOptions.forestThreshold.get() && noiseMap[y][x] >= genOptions.grassThreshold.get()) {
                        Vector2D pos = toWorldSpace(x, y);
                        if (pos != null && GameData.random.nextDouble() < genOptions.forestDensity.get()) {
                            Entity tree = treeSPI.create(null);

                            tree.get(PositionComponent.class).position.set(pos.x + 0.5, pos.y + 0.5);
                            Engine.addEntity(tree);
                            mapEntities.add(tree);
                        }
                    }
                }
                else tiles[y][x] = new Tile(Tile.Type.WATER);



            }
        }
    }

    private void refineMap() {
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
                addFoam(x, y);


            }
        }

        // water OBs
        // first we scan each row
        for (int y = (GameData.worldSize / 2); y > -GameData.worldSize / 2; y--) {
            for (int x = (-GameData.worldSize / 2); x < GameData.worldSize / 2; x++) {
                Tile tile = getTile(x, y);
                if (tile == null) continue;
                boolean sea = tile.type == Tile.Type.WATER;
                int startX = x;
                while (sea) {
                    x++;
                    tile = getTile(x, y);
                    if (tile == null || tile.type != Tile.Type.WATER) {
                        sea = false;
                        double width = x - startX;
                        if (width == 0) break;
                        Entity ob = new Entity();
                        Vector2D pos = new Vector2D(startX + width * 0.5, y - 0.5);
                        ob.add(new PositionComponent(pos));
                        ob.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
                        OBShape obShape = new OBShape(OBShape.Direction.HORIZONTAL, width, 0.95);
                        ob.add(new CollisionComponent(obShape));
                        Engine.addEntity(ob);
                        mapEntities.add(ob);
                    }
                }
            }
        }
        // then we scan each column
        for (int x = (-GameData.worldSize / 2); x < GameData.worldSize / 2; x++) {
            for (int y = (GameData.worldSize / 2); y > -GameData.worldSize / 2; y--) {
                Tile tile = getTile(x, y);
                if (tile == null) continue;
                boolean sea = tile.type == Tile.Type.WATER;
                int startY = y;
                while (sea) {
                    y--;
                    tile = getTile(x, y);
                    if (tile == null || tile.type != Tile.Type.WATER) {
                        sea = false;
                        double height = startY - y;
                        if (height == 0) break;
                        Entity ob = new Entity();
                        Vector2D pos = new Vector2D(x + 0.5, y + height * 0.5);
                        ob.add(new PositionComponent(pos));
                        ob.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
                        OBShape obShape = new OBShape(OBShape.Direction.VERTICAL, 0.95, height);
                        ob.add(new CollisionComponent(obShape));
                        Engine.addEntity(ob);
                        mapEntities.add(ob);
                    }
                }
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
            assert foamSPI != null;
            Entity foam = foamSPI.create(null);
            foam.get(PositionComponent.class).position.set(x + 0.5, y - 0.5);
            addEntity(foam);
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

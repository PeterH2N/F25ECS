package dk.sdu.petni23.animationnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.ConnectingSpriteComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.shape.OBShape;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.common.world.GameWorld;
import dk.sdu.petni23.common.world.mapgen.Tile;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.services.ISystem;

import java.util.ArrayList;
import java.util.List;

public class ConnectingSpriteSystem implements ISystem {

    private static final ConnectingSpriteComponent.Type[][] connectGrid = new ConnectingSpriteComponent.Type[GameData.worldSize][GameData.worldSize];
    private static final List<Entity> entities = new ArrayList<>();

    @Override
    public void update(double deltaTime) {
        // nothing actually?
    }

    public static void scanWorld() {
        entities.forEach(Engine::removeEntity);
        entities.clear();
        // reset grid
        for (int i = 0; i < GameData.worldSize; i++) {
            for (int j = 0; j < GameData.worldSize; j++) {
                connectGrid[i][j] = ConnectingSpriteComponent.Type.NONE;
            }
        }
        var nodes = Engine.getNodes(ConnectingSpriteNode.class);

        for (var node : nodes) {
            // place them into the world
            var pos = GameWorld.toTileSpace(node.positionComponent.position);
            connectGrid[(int) pos.y][(int) pos.x] = node.connectingSpriteComponent.type;
        }

        for (var node : nodes) {
            // set their placement
            // get position in tile space
            var type = node.connectingSpriteComponent.type;
            var pos = GameWorld.toTileSpace(node.positionComponent.position);
            int x = (int) pos.x;
            int y = (int) pos.y;
            ConnectingSpriteComponent.Type nType = getType(x, y-1);
            ConnectingSpriteComponent.Type sType = getType(x, y+1);
            ConnectingSpriteComponent.Type eType = getType(x+1, y);
            ConnectingSpriteComponent.Type wType = getType(x-1, y);

            boolean north = nType != null && nType == type;
            boolean south = sType != null && sType == type;
            boolean east = eType != null && eType == type;
            boolean west = wType != null && wType == type;

            // placement
            int pX = 3;
            int pY = 4;
            if (north) pY = 2;
            if (south) pY = 0;
            if (north && south) pY = 1;

            if (west) pX = 2;
            if (east) pX = 0;
            if (west && east) pX = 1;

            // set sprite column/row based on placement
            node.spriteComponent.column = pX;
            node.spriteComponent.row = pY;
        }

        for (var currentType : ConnectingSpriteComponent.Type.values()) {
            if (currentType == ConnectingSpriteComponent.Type.NONE) continue;
            for (int y = (GameData.worldSize / 2); y > -GameData.worldSize / 2; y--) {
                for (int x = (-GameData.worldSize / 2); x < GameData.worldSize / 2; x++) {
                    var type = getType(x, y);

                    boolean sea = type == currentType;
                    int startX = x;
                    while (sea) {
                        x++;
                        type = getType(x, y);
                        if (type != currentType) {
                            sea = false;
                            double width = x - startX;
                            if (width == 0) break;
                            Entity ob = new Entity();
                            Vector2D pos = new Vector2D(startX + width * 0.5, y + 0.5);
                            ob.add(new PositionComponent(pos));
                            ob.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
                            OBShape obShape = new OBShape(OBShape.Direction.HORIZONTAL, width, 0.95);
                            ob.add(new CollisionComponent(obShape));
                            Engine.addEntity(ob);
                            entities.add(ob);
                        }
                    }
                }
            }
            for (int x = (-GameData.worldSize / 2); x < GameData.worldSize / 2; x++) {
                for (int y = (GameData.worldSize / 2); y > -GameData.worldSize / 2; y--) {
                    var type = getType(x, y);
                    boolean sea = type == currentType;
                    int startY = y;
                    while (sea) {
                        y--;
                        type = getType(x, y);
                        if (type != currentType) {
                            sea = false;
                            double height = startY - y;
                            if (height == 0) break;
                            Entity ob = new Entity();
                            Vector2D pos = new Vector2D(x + 0.5, y + height * 0.5 + 1);
                            ob.add(new PositionComponent(pos));
                            ob.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
                            OBShape obShape = new OBShape(OBShape.Direction.VERTICAL, 0.95, height);
                            ob.add(new CollisionComponent(obShape));
                            Engine.addEntity(ob);
                            entities.add(ob);
                        }
                    }
                }
            }
        }

    }

    // arguments in world space
    static ConnectingSpriteComponent.Type getType(int x, int y) {
        var tileSpace = GameWorld.toTileSpace(x, y);
        x = (int) tileSpace.x;
        y = (int) tileSpace.y;
        if (x > GameData.worldSize - 1 || x < 0 || y > GameData.worldSize - 1 || y < 0) return ConnectingSpriteComponent.Type.NONE;
        return connectGrid[y][x];
    }

    @Override
    public int getPriority() {
        return Priority.PROCESSING.get();
    }
}

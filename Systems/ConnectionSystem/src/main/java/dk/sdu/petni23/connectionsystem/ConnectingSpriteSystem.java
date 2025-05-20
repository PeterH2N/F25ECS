package dk.sdu.petni23.connectionsystem;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.ConnectingSpriteComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.shape.OBShape;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.common.world.GameWorld;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.ISystem;

import java.util.ArrayList;
import java.util.List;

public class ConnectingSpriteSystem extends ISystem {

    private static final IEntitySPI.Type[][] connectGrid = new IEntitySPI.Type[GameData.worldSize][GameData.worldSize];
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
                connectGrid[i][j] = IEntitySPI.Type.NONE;
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
            var nType = getTypeTileSpace(x, y-1);
            var sType = getTypeTileSpace(x, y+1);
            var eType = getTypeTileSpace(x+1, y);
            var wType = getTypeTileSpace(x-1, y);

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



    }

    // arguments in world space
    static IEntitySPI.Type getType(int x, int y) {
        var tileSpace = GameWorld.toTileSpace(x, y);
        x = (int) tileSpace.x;
        y = (int) tileSpace.y;
        if (x > GameData.worldSize - 1 || x < 0 || y > GameData.worldSize - 1 || y < 0) return IEntitySPI.Type.NONE;
        return connectGrid[y][x];
    }

    static IEntitySPI.Type getTypeTileSpace(int x, int y) {
        if (x > GameData.worldSize - 1 || x < 0 || y > GameData.worldSize - 1 || y < 0) return IEntitySPI.Type.NONE;
        return connectGrid[y][x];
    }

    @Override
    public int getPriority() {
        return Priority.PROCESSING.get();
    }
}

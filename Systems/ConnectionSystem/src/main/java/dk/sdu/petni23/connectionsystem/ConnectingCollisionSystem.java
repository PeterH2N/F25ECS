package dk.sdu.petni23.connectionsystem;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.ConnectingCollisionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
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

public class ConnectingCollisionSystem implements ISystem {

    private static final IEntitySPI.Type[][] connectGrid = new IEntitySPI.Type[GameData.worldSize][GameData.worldSize];
    private static final List<Entity> entities = new ArrayList<>();
    @Override
    public void update(double deltaTime) {

    }

    static void scanWorld() {
        entities.forEach(Engine::removeEntity);
        entities.clear();
        // reset grid
        for (int i = 0; i < GameData.worldSize; i++) {
            for (int j = 0; j < GameData.worldSize; j++) {
                connectGrid[i][j] = IEntitySPI.Type.NONE;
            }
        }
        var nodes = Engine.getNodes(ConnectingCollisionNode.class);

        for (var node : nodes) {
            // place them into the world
            var pos = GameWorld.toTileSpace(node.positionComponent.position);
            connectGrid[(int) pos.y][(int) pos.x] = node.connectingCollisionComponent.type;
        }

        for (var currentType : ConnectingCollisionComponent.insets.keySet()) {
            if (currentType == IEntitySPI.Type.NONE) continue;
            double inset = 0;
            Double i = ConnectingCollisionComponent.insets.get(currentType);
            if (i != null) inset = i;
            for (int y = (GameData.worldSize / 2); y > -GameData.worldSize / 2; y--) {
                for (int x = (-GameData.worldSize / 2); x < GameData.worldSize / 2; x++) {
                    var type = getType(x, y);

                    if (type == currentType) {
                        double width = 1;
                        Entity ob = new Entity(null);
                        Vector2D pos = new Vector2D(x + width * 0.5, y + 0.5);
                        pos = ob.add(new PositionComponent(pos)).position;
                        ob.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

                        double height = 1 - inset;
                        if (getType(x, y+1) == currentType) {
                            height += inset * 0.5;
                            pos.y += inset * 0.25;
                        } else {
                            height -= 0.015;
                            pos.y -= 0.015;
                        }
                        if (getType(x, y-1) == currentType) {
                            height += inset * 0.5;
                            pos.y -= inset * 0.25;
                        } else {
                            height -= 0.015;
                            pos.y += 0.015;
                        }

                        OBShape obShape = new OBShape(OBShape.Direction.HORIZONTAL, width - inset, height - 0.03);
                        ob.add(new CollisionComponent(obShape));
                        Engine.addEntity(ob);
                        entities.add(ob);
                    }
                }
            }
            for (int x = (-GameData.worldSize / 2); x < GameData.worldSize / 2; x++) {
                for (int y = (GameData.worldSize / 2); y > -GameData.worldSize / 2; y--) {
                    var type = getType(x, y);

                    if (type == currentType) {
                        double height = 1;
                        Entity ob = new Entity(null);
                        Vector2D pos = new Vector2D(x + 0.5, y + height * 0.5);
                        pos = ob.add(new PositionComponent(pos)).position;
                        ob.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));

                        double width = 1 - inset;
                        if (getType(x+1, y) == currentType) {
                            width += inset * 0.5;
                            pos.x += inset * 0.25;
                        } else {
                            width -= 0.015;
                            pos.x -= 0.015;
                        }
                        if (getType(x-1, y) == currentType) {
                            width += inset * 0.5;
                            pos.x -= inset * 0.25;
                        } else {
                            width -= 0.015;
                            pos.x += 0.015;
                        }
                        OBShape obShape = new OBShape(OBShape.Direction.VERTICAL, width, height - inset);
                        ob.add(new CollisionComponent(obShape));
                        Engine.addEntity(ob);
                        entities.add(ob);
                    }
                }
            }
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

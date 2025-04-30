package dk.sdu.petni23.common.world;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.util.Collider;
import dk.sdu.petni23.common.misc.Manifold;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.common.world.mapgen.GameMap;
import dk.sdu.petni23.common.world.mapgen.MapGenOptions;
import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.node.Node;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GameWorld
{
    public final List<Manifold> collisionManifolds = new ArrayList<>();
    public final List<Manifold> hitBoxManifolds = new ArrayList<>();
    public final Map<Node, Collider> collisionColliders = new HashMap<>();
    public final Map<Node, Collider> hitBoxColliders = new HashMap<>();

    public static final List<Collider>[][] collisionGrid = (ArrayList<Collider>[][])new ArrayList[(int) GameData.worldSize][(int) GameData.worldSize];
    public static final List<Collider>[][] hitBoxGrid = (ArrayList<Collider>[][])new ArrayList[(int) GameData.worldSize][(int) GameData.worldSize];
    public Entity nexus;

    public final GameMap map;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().
            registerTypeHierarchyAdapter(Component.class, new ComponentTypeAdapter()).
            create();

    public GameWorld(MapGenOptions options)
    {
        this.map = new GameMap(options);
    }

    public static Vector2D toTileSpace(double x, double y) {
        int X = (int) Math.floor(x + GameData.worldSize * 0.5);
        int Y = (int) Math.floor(-y + GameData.worldSize * 0.5);

        if (X < 0) X = 0;
        if (Y < 0) Y = 0;
        if (X >= GameData.worldSize - 1) X = GameData.worldSize - 1;
        if (Y >= GameData.worldSize - 1) Y = GameData.worldSize - 1;

        return new Vector2D(X, Y);
    }

    public static Vector2D toTileSpace(Vector2D v) {
        return toTileSpace(v.x, v.y);
    }



    public static Vector2D toWorldSpace(int x, int y) {
        int X = (int) (x - GameData.worldSize * 0.5);
        int Y = (int) -(y - GameData.worldSize * 0.5);

        return new Vector2D(X, Y);
    }
    public static Vector2D toWorldSpace(Vector2D v) {
        return toWorldSpace((int) v.x, (int) v.y);
    }

    public void load() {
        var userDir = System.getProperty("user.dir") + File.separator;
        // remove all entities
        Engine.getEntities().forEach(Engine::removeEntity);
        GameData.world.map.genMap();

        try {
            String file = Files.readString(Paths.get(userDir + "/save/save.txt"));
            //System.out.println(file);

            var type = new TypeToken<HashMap<IEntitySPI.Type, HashMap<Long, ArrayList<Component>>>>() {}.getType();

            Map<IEntitySPI.Type, Map<Long, Collection<Component>>> save = gson.fromJson(file, type);


            for (var entitySPI : Engine.getEntitySPIs()) {
                var entitiesMap = save.get(entitySPI.getType());
                if (entitiesMap != null) {
                    entitiesMap.values().forEach(components -> {
                        Entity entity = entitySPI.create(null);
                        // add each component if not present, or update values if present
                        components.forEach(component -> {
                            Component entityComponent = entity.get(component.getClass());
                            if (entityComponent == null) {
                                entity.add(component);
                                return;
                            }
                            var fields = component.getClass().getFields();
                            Arrays.stream(fields).forEach(field -> {
                                if (Modifier.isTransient(field.getModifiers())) return;
                                field.setAccessible(true);
                                try {
                                    field.set(entityComponent, field.get(component));
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        });
                        List<Class<? extends Component>> toRemove = new ArrayList<>();
                        // remove components that are part of the entity, but not saved to file
                        entity.getComponents().values().forEach(eComponent -> {
                            if (components.stream().noneMatch(component -> eComponent.getClass().equals(component.getClass()))) {
                                toRemove.add(eComponent.getClass());
                            }
                        });
                        toRemove.forEach(entity::remove);

                        Engine.addEntity(entity);
                    });
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        var userDir = System.getProperty("user.dir") + File.separator;

        var allEntities = Engine.getEntities();

        Map<IEntitySPI.Type, Map<Long, Collection<Component>>> savedEntities = new HashMap<>();

        for (var entity : allEntities) {
            if (entity.getType() != null) {
                savedEntities.putIfAbsent(entity.getType(), new HashMap<>());
                var entities = savedEntities.get(entity.getType());
                entities.put(entity.getId(), entity.getComponents().values());
            }
        }

        String serialized = gson.toJson(savedEntities);

        //System.out.println(serialized);
        try {
            Files.write(Paths.get(userDir + "/save/save.txt"), Collections.singletonList(serialized), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class ComponentTypeAdapter implements JsonSerializer<Component>, JsonDeserializer<Component> {
        @Override
        public JsonElement serialize(Component component, Type type, JsonSerializationContext jsonSerializationContext) {

            JsonElement elem = new GsonBuilder().registerTypeHierarchyAdapter(Shape.class, new ShapeTypeAdapter()).create().toJsonTree(component);
            elem.getAsJsonObject().addProperty("classType", component.getClass().getName());
            return elem;
        }

        @Override
        public Component deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String typeName = jsonObject.get("classType").getAsString();

            try {
                Class<? extends Component> cls = (Class<? extends Component>) Class.forName(typeName);
                return new GsonBuilder().registerTypeHierarchyAdapter(Shape.class, new ShapeTypeAdapter()).create().fromJson(jsonElement, cls);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }
    }

    static class ShapeTypeAdapter implements JsonSerializer<Shape>, JsonDeserializer<Shape> {

        @Override
        public Shape deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String typeName = jsonObject.get("classType").getAsString();

            try {
                Class<? extends Shape> cls = (Class<? extends Shape>) Class.forName(typeName);
                return new Gson().fromJson(jsonElement, cls);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }

        @Override
        public JsonElement serialize(Shape shape, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonElement elem = new Gson().toJsonTree(shape);
            elem.getAsJsonObject().addProperty("classType", shape.getClass().getName());
            return elem;
        }
    }
}

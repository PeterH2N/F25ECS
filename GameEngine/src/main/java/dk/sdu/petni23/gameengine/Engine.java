package dk.sdu.petni23.gameengine;

import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.services.IPhysicsSystem;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.IRenderSystem;
import dk.sdu.petni23.gameengine.services.ISystem;

import java.util.*;

public class Engine
{
    private static final int physicsSteps = 2;
    private final static Map<Long, Entity> entities = new HashMap<>();
    private final static List<Node> nodes = new ArrayList<>();
    private final static List<ISystem> systems = getServices(ISystem.class);
    private final static List<IPhysicsSystem> physicsSystems = getServices(IPhysicsSystem.class);
    private final static List<IRenderSystem> renderingSystems = getServices(IRenderSystem.class);
    private final static Collection<? extends IPluginService> plugins = getServices(IPluginService.class);
    private final static Collection<? extends INodeSPI> nodeSPIs = getServices(INodeSPI.class);
    private final static List<IEntitySPI> entitySPIs = getServices(IEntitySPI.class);

    public static Entity addEntity(Entity entity) {
        if (entity == null) return null;
        entities.put(entity.getId(), entity);
        for (var spi : nodeSPIs) {
            if (spi.requiredComponentsContained(entity.getComponentClasses())) {
                var node = spi.createNode(entity);
                nodes.add(node);
                node.onAdd();
            }
        }
        return entity;
    }

    public static List<Entity> getEntities() {
        return entities.values().stream().toList();
    }

    public static Entity getEntity(Long ID) {
        return entities.get(ID);
    }

    public static void removeEntity(Entity entity) {
        if (entities.get(entity.getId()) != null) {
            // store removed nodes to call their onRemove method afterward
            List<Node> removedNodes = new ArrayList<>();

            nodes.removeIf(node -> {
                if (node.getEntityID() == entity.getId()) {
                    removedNodes.add(node);
                    return true;
                }
                return false;
            });
            removedNodes.forEach(Node::onRemove);
            entities.remove(entity.getId());
        }
    }

    public static void removeEntity(long id) {
        Entity entity = entities.get(id);
        if (entity != null)
            removeEntity(entity);
    }

    public static void start() {
        // sort systems based on their priority
        systems.sort(Comparator.comparingInt(ISystem::getPriority));
        physicsSystems.sort(Comparator.comparingInt(IPhysicsSystem::getPriority));

        for (var plugin : plugins) {
            plugin.start();
        }
    }

    public static void stop() {
        for (var plugin : plugins) {
            plugin.stop();
        }
    }

    public static void update(double deltaTime) {
        for (var system : systems) {
            system.update(deltaTime);
        }

        for (var system : renderingSystems) {
            system.render();
        }

        double timeStep = deltaTime / physicsSteps;
        for (int i = 0; i < physicsSteps; i++) {
            for (var system : physicsSystems) {
                system.step(timeStep);
            }
        }
    }

    public static <T extends Node> List<T> getNodes(Class<T> nodeType) {
        List<T> r = new ArrayList<>();
        for (Node node : nodes) {
                if (nodeType.isInstance(node))
                    r.add((T)node);
        }
        return r;
    }

    public static List<Node> getNodes() {
        return new ArrayList<>(nodes);
    }

    private static <T> ArrayList<T> getServices(Class<T> c) {
        return new ArrayList<>(ServiceLoader.load(c).stream().map(ServiceLoader.Provider::get).toList()) ;
    }


    public static List<IEntitySPI> getEntitySPIs()
    {
        return entitySPIs;
    }

    public static IEntitySPI getEntitySPI(IEntitySPI.Type type) {
        for (var spi : Engine.getEntitySPIs()) {
            if (spi.getType() == type)
                return spi;
        }
        return null;
    }
}

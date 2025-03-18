package dk.sdu.petni23.gameengine;

import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.services.IPhysicsSystem;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;
import java.util.*;

public class Engine
{
    private static final int physicsSteps = 4;
    private final static List<Entity> entities = new ArrayList<>();
    private final static List<Node> nodes = new ArrayList<>();
    private final static List<ISystem> systems = getServices(ISystem.class);
    private final static List<IPhysicsSystem> physicsSystems = getServices(IPhysicsSystem.class);
    private final static Collection<? extends IPluginService> plugins = getServices(IPluginService.class);
    private final static Collection<? extends INodeSPI> nodeSPIs = getServices(INodeSPI.class);

    private final static List<IEntitySPI> entitySPIs = getServices(IEntitySPI.class);
    public static void addEntity(Entity entity) {
        entities.add(entity);
        for (var spi : nodeSPIs) {
            if (spi.requiredComponentsContained(entity.getComponentClasses())) {
                nodes.add(spi.createNode(entity));
            }
        }
    }

    public static void removeEntity(Entity entity) {
        entities.remove(entity);
        nodes.removeIf(node -> node.getComponents().stream().anyMatch(c -> entity.getComponents().contains(c)));
    }

    public static void addNode(Node node) {
        nodes.add(node);
    }

    public static void start() {
        // sort systems based on their priority
        systems.sort(Comparator.comparingInt(ISystem::getPriority));

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

        for (var system : physicsSystems) {
            system.preUpdate();
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

    private static <T> ArrayList<T> getServices(Class<T> c) {
        return new ArrayList<>(ServiceLoader.load(c).stream().map(ServiceLoader.Provider::get).toList()) ;
    }


    public static List<IEntitySPI> getEntitySPIs()
    {
        return entitySPIs;
    }
}

package dk.sdu.petni23.common;

import dk.sdu.petni23.common.entity.Entity;
import dk.sdu.petni23.common.entity.IEntitySPI;
import dk.sdu.petni23.common.node.INodeSPI;
import dk.sdu.petni23.common.node.Node;
import dk.sdu.petni23.common.services.IPluginService;
import dk.sdu.petni23.common.services.IPostProcessingSystem;
import dk.sdu.petni23.common.services.IProcessingSystem;
import java.util.*;

public class Engine
{
    private final static List<Entity> entities = new ArrayList<>();
    private final static List<IProcessingSystem> systems = getServices(IProcessingSystem.class);
    private final static Collection<? extends IPostProcessingSystem> postSystems = getServices(IPostProcessingSystem.class);
    private final static Collection<? extends IPluginService> plugins = getServices(IPluginService.class);
    private final static List<Node> nodes = new ArrayList<>();
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
    }

    public static <T extends Node> List<T> getNodes(Class<T> nodeType) {
        List<T> r = new ArrayList<>();
        for (Node node : nodes) {
                if (nodeType.isInstance(node))
                    r.add((T)node);
        }
        return r;
    }

    private static <T> List<T> getServices(Class<T> c) {
        return ServiceLoader.load(c).stream().map(ServiceLoader.Provider::get).toList();
    }


    public static List<IEntitySPI> getEntitySPIs()
    {
        return entitySPIs;
    }
}

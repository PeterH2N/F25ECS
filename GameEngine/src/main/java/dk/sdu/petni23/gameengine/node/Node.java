package dk.sdu.petni23.gameengine.node;



import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.Entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Node
{
    public Node(Entity entity) {
        initNode(entity, this);
    }

    public static List<Class<? extends Component>> getRequiredComponentClasses(Class<? extends Node> nodeClass) {
        List<Class<? extends Component>> componentClasses = new ArrayList<>();
        for (Field field : nodeClass.getDeclaredFields()) {
            Class<?> type = field.getType();
            if (Component.class.isAssignableFrom(type) && !field.isAnnotationPresent(Optional.class)) {
                componentClasses.add((Class<? extends Component>) type);
            }
        }
        return componentClasses;
    }

    public static <T extends Node> void initNode(Entity entity, T node) {
        var components = entity.getComponents();
        var fields = node.getClass().getFields();
        for (Component component : components) {
            for (Field field : fields) {
                if (component.getClass().isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    try {
                        field.set(node, component);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public List<Component> getComponents() {
        List<Component> components = new ArrayList<>();
        for (Field field : this.getClass().getFields()) {
            if (field.getType().isAssignableFrom(Component.class)) {
                field.setAccessible(true);
                try {
                    components.add((Component)field.get(this));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return components;
    }
}

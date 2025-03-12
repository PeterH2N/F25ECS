package dk.sdu.petni23.common.entity;

import dk.sdu.petni23.common.components.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Entity
{
    private static long idCount = 0;
    private final Long id = idCount++;
    private final Map<Class<? extends Component>, Component> components = new HashMap<>();

    public void add(Component component) {
        Class<? extends Component> c = component.getClass();
        components.put(c, component);
    }

    public void remove(Class<? extends Component> c) {
        components.remove(c);
    }

    public Component get(Class<? extends Component> c) {
        return components.get(c);
    }

    public Collection<Class<? extends Component>> getComponentClasses() {
        return components.keySet();
    }

    public Collection<Component> getComponents() {
        return components.values();
    }

    public Long getId()
    {
        return id;
    }
}

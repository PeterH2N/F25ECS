package dk.sdu.petni23.gameengine.entity;

import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.Engine;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Entity implements Serializable
{
    private static long idCount = 0;
    private final Long id = idCount++;
    private final Map<Class<? extends Component>, Component> components = new HashMap<>();
    private boolean onMap;

    public boolean isOnMap(){
        return onMap;
    }

    public void addToMap(){
        this.onMap = true;
    }

    public <T extends Component> T add(T component) {
        Class<? extends Component> c = component.getClass();
        components.put(c, component);
        if (Engine.getEntity(id) == this) {
            Engine.removeEntity(id);
            Engine.addEntity(this);
        }
        return component;
    }

    public void remove(Class<? extends Component> c) {
        components.remove(c);
    }

    public <T extends Component> T get(Class<T> c) {
        return (T) components.get(c);
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

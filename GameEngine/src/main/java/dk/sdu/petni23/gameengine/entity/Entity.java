package dk.sdu.petni23.gameengine.entity;

import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.Engine;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Entity implements Serializable {
    private static long idCount = 0;
    private final Long id = idCount++;
    private final Map<Class<? extends Component>, Component> components = new HashMap<>();
    private boolean onMap;

    private double rotation = 0.0;

    public boolean isOnMap() {
        return onMap;
    }

    public void addToMap() {
        this.onMap = true;
    }

    // Add components to the entity
    public <T extends Component> T add(T component) {
        Class<? extends Component> c = component.getClass();
        components.put(c, component);
        if (Engine.getEntity(id) == this) {
            Engine.removeEntity(id);
            Engine.addEntity(this);
        }
        return component;
    }

    // Remove components from the entity
    public void remove(Class<? extends Component> c) {
        components.remove(c);
        if (Engine.getEntity(id) == this) {
            Engine.removeEntity(id);
            Engine.addEntity(this);
        }
    }

    // Get the component by its class type
    public <T extends Component> T get(Class<T> c) {
        return (T) components.get(c);
    }

    // Get all component classes
    public Collection<Class<? extends Component>> getComponentClasses() {
        return components.keySet();
    }

    // Get all components
    public Collection<Component> getComponents() {
        return components.values();
    }

    // Get the entity's unique ID
    public Long getId() {
        return id;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return this.rotation;
    }
}

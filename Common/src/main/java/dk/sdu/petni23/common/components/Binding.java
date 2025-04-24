package dk.sdu.petni23.common.components;

import dk.sdu.petni23.gameengine.entity.Entity;

import java.io.Serializable;

public interface Binding {

    void doBinding(Entity e1, Entity e2);
}

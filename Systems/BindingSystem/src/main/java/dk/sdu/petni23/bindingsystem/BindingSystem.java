package dk.sdu.petni23.bindingsystem;

import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.services.ISystem;

public class BindingSystem implements ISystem {
    @Override
    public void update(double deltaTime) {
        for (BindingNode node : Engine.getNodes(BindingNode.class)) {
            node.bindingComponent.bindings.keySet().forEach(entity -> node.bindingComponent.bindings.get(entity).doBinding(Engine.getEntity(node.getEntityID()), entity));
        }
    }

    @Override
    public int getPriority() {
        return Priority.PROCESSING.get();
    }
}

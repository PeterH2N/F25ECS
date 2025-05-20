package dk.sdu.petni23.bindingsystem;

import dk.sdu.petni23.common.components.BindingComponent;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class BindingNode extends Node {

    public BindingComponent bindingComponent;

    public BindingNode(Entity entity) {
        super(entity);
    }

    @Override
    public void onRemove() {
        bindingComponent.bindings.keySet().forEach(Engine::removeEntity);
    }

    @Override
    public void onAdd() {
        bindingComponent.bindings.keySet().forEach(Engine::addEntity);
    }
}

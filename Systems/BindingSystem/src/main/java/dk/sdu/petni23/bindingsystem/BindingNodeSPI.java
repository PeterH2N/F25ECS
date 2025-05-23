package dk.sdu.petni23.bindingsystem;

import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.node.Node;

import java.util.List;

public class BindingNodeSPI extends INodeSPI {

    private static final List<Class<? extends Component>> requiredComponents = Node.getRequiredComponentClasses(BindingNode.class);
    @Override
    public List<Class<? extends Component>> getRequiredComponents() {
        return requiredComponents;
    }

    @Override
    public Node createNode(Entity entity) {
        return new BindingNode(entity);
    }
}

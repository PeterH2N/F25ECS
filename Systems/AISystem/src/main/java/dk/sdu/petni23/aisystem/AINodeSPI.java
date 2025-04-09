package dk.sdu.petni23.aisystem;

import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.node.Node;

import java.util.List;

public class AINodeSPI extends INodeSPI {
    private static final List<Class<? extends Component>> requiredComponents = Node.getRequiredComponentClasses(AINode.class);
    @Override
    public List<Class<? extends Component>> getRequiredComponents() {
        return requiredComponents;
    }

    @Override
    public Node createNode(Entity entity) {
        return new AINode(entity);
    }
}

package dk.sdu.petni23.connectionsystem;

import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.node.Node;

import java.util.List;

public class ConnectingCollisionNodeSPI extends INodeSPI {

    private static final List<Class<? extends Component>> requiredComponents = Node.getRequiredComponentClasses(ConnectingCollisionNode.class);
    @Override
    public List<Class<? extends Component>> getRequiredComponents() {
        return requiredComponents;
    }

    @Override
    public Node createNode(Entity entity) {
        return new ConnectingCollisionNode(entity);
    }
}

package dk.sdu.petni23.gameflow;

import java.util.List;

import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.INodeSPI;
import dk.sdu.petni23.gameengine.node.Node;

public class SpawnNodeSPI extends INodeSPI {
    private static final List<Class<? extends Component>> requiredComponents = Node.getRequiredComponentClasses(SpawnNode.class);
    
    
    @Override
    public List<Class<? extends Component>> getRequiredComponents() {
        return requiredComponents;
    }

    @Override
    public Node createNode(Entity entity) {
        return new SpawnNode(entity);
    }
}

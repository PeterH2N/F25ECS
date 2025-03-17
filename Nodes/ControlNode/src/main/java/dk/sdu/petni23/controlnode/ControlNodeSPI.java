package dk.sdu.petni23.controlnode;

import dk.sdu.petni23.common.components.Component;
import dk.sdu.petni23.common.entity.Entity;
import dk.sdu.petni23.common.node.INodeSPI;
import dk.sdu.petni23.common.node.Node;

import java.util.List;

public class ControlNodeSPI extends INodeSPI
{
    private static final List<Class<? extends Component>> requiredComponents = Node.getRequiredComponentClasses(ControlNode.class);
    @Override
    public List<Class<? extends Component>> getRequiredComponents()
    {
        return requiredComponents;
    }

    @Override
    public Node createNode(Entity entity)
    {
        return new ControlNode(entity);
    }
}

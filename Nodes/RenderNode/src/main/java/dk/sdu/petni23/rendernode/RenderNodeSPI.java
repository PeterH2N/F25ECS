package dk.sdu.petni23.rendernode;

import dk.sdu.petni23.common.components.Component;
import dk.sdu.petni23.common.entity.Entity;
import dk.sdu.petni23.common.node.INodeSPI;
import dk.sdu.petni23.common.node.Node;

import java.util.List;

public class RenderNodeSPI extends INodeSPI
{
    private static final List<Class<? extends Component>> requiredComponents = Node.getComponentClasses(RenderNode.class);
    @Override
    public List<Class<? extends Component>> getRequiredComponents()
    {
        return requiredComponents;
    }

    @Override
    public Node createNode(Entity entity)
    {
        return new RenderNode(entity);
    }
}

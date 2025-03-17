package dk.sdu.petni23.spritenode;

import dk.sdu.petni23.common.components.Component;
import dk.sdu.petni23.common.entity.Entity;
import dk.sdu.petni23.common.node.INodeSPI;
import dk.sdu.petni23.common.node.Node;

import java.util.List;

public class SpriteNodeSPI extends INodeSPI
{
    private static final List<Class<? extends Component>> requiredComponents = Node.getRequiredComponentClasses(SpriteNode.class);
    @Override
    public List<Class<? extends Component>> getRequiredComponents()
    {
        return requiredComponents;
    }

    @Override
    public Node createNode(Entity entity)
    {
        return new SpriteNode(entity);
    }
}

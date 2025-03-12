package dk.sdu.petni23.common.node;

import dk.sdu.petni23.common.components.Component;
import dk.sdu.petni23.common.entity.Entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public abstract class INodeSPI
{
    public boolean requiredComponentsContained(Collection<Class<? extends Component>> componentClasses) {
        return new HashSet<>(componentClasses).containsAll(getRequiredComponents());
    }

    public abstract List<Class<? extends Component>> getRequiredComponents();

    public abstract Node createNode(Entity entity);
}

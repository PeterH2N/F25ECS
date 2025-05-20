package dk.sdu.petni23.common.components.ai;

import dk.sdu.petni23.gameengine.Component;

import java.util.List;

public class AIComponent extends Component
{
    public double range = 10;
    public final Type type;
    public final List<Type> TargetPriorityList;
    public final Priority priority;

    public AIComponent(Type type, List<Type> targetPriorityList, Priority priority) {
        this.type = type;
        TargetPriorityList = targetPriorityList;
        this.priority = priority;
    }

    public enum Priority {
        CLOSEST,
        WEAKEST,
        STRONGEST
    }
    public enum Type {
        CHARACTER,
        TOWER,
        WORKER,
        NEXUS,
        TREE,
        MINE,
        STONE,
        ALL; // only for target
    }
}

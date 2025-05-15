package dk.sdu.petni23.common.components;

import dk.sdu.petni23.gameengine.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacementComponent extends Component {
    public final transient Map<Class<? extends Component>, Component> toAdd = new HashMap<>();
    public final transient List<Class<? extends Component>> toRemove = new ArrayList<>();
    public transient Dispatch onPlace = null;
    public PlacementComponent(Component... toAdd)
    {
        for (Component component : toAdd) {
            this.toAdd.put(component.getClass(), component);
        }
    }


}

package dk.sdu.petni23.common.components;

import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.gameengine.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacementComponent extends Component {
    public final transient Map<Class<? extends Component>, Component> components = new HashMap<>();
    public final transient List<Class<? extends Component>> toRemove = new ArrayList<>();
    public PlacementComponent(Component... components)
    {
        for (Component component : components) {
            this.components.put(component.getClass(), component);
        }
    }


}

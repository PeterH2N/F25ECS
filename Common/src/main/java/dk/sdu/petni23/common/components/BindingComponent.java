package dk.sdu.petni23.common.components;

import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BindingComponent extends Component {
    public transient final Map<Entity, Binding> bindings = new HashMap<>();
}

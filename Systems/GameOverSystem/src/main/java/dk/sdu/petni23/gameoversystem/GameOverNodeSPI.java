package dk.sdu.petni23.gameoversystem;

import dk.sdu.petni23.common.components.gameflow.GameOverComponent;
import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.INodeSPI;

import java.util.List;

public class GameOverNodeSPI extends INodeSPI {

    @Override
    public List<Class<? extends Component>> getRequiredComponents() {
        return List.of(GameOverComponent.class);
    }

    @Override
    public GameOverNode createNode(Entity entity) {
        return new GameOverNode(entity);
    }
}

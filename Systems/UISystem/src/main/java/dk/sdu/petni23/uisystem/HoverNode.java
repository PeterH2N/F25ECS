package dk.sdu.petni23.uisystem;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.ui.HoverInteractComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class HoverNode extends Node {

    public PositionComponent positionComponent;

    public HoverInteractComponent hoverInteractComponent;

    public HoverNode(Entity entity) {
        super(entity);
    }
    @Override
    public void onRemove() {
        GameData.uiPane.getChildren().remove(hoverInteractComponent.hoverNode);
        GameData.uiPane.getChildren().remove(hoverInteractComponent.interactNode);
    }

    @Override
    public void onAdd() {
        GameData.uiPane.getChildren().add(hoverInteractComponent.hoverNode);
        GameData.uiPane.getChildren().add(hoverInteractComponent.interactNode);
    }

}

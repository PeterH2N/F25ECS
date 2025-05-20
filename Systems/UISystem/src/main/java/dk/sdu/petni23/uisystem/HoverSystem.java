package dk.sdu.petni23.uisystem;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;

public class HoverSystem implements ISystem {

    @Override
    public void update(double deltaTime) {
        for (HoverNode node : Engine.getNodes(HoverNode.class)) {
            // set position
            var pos = GameData.toScreenSpace(node.positionComponent.position.getAdded(node.hoverInteractComponent.offset));
            var hover = node.hoverInteractComponent.hoverNode;
            var interact = node.hoverInteractComponent.interactNode;
            double w = hover.getBoundsInLocal().getWidth();
            double h = hover.getBoundsInLocal().getHeight();
            double x = pos.x - w / 2;
            double y = pos.y - h / 2;
            hover.setTranslateX(x);
            hover.setTranslateY(y);
            interact.setTranslateX(pos.x - interact.getBoundsInLocal().getWidth() / 2);
            interact.setTranslateY(pos.y - interact.getBoundsInLocal().getHeight() / 2);
            hover.setScaleX(GameData.getTileRatio());
            hover.setScaleY(GameData.getTileRatio());
        }
    }

    @Override
    public int getPriority() {
        return Priority.PREPROCESSING.get();
    }
}

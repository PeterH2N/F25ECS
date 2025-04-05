package dk.sdu.petni23.controlnode;


import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;
import javafx.scene.input.MouseButton;

public class ControlSystem implements ISystem
{

    @Override
    public void update(double deltaTime)
    {
        for (ControlNode node : Engine.getNodes(ControlNode.class)) {
            var v = new Vector2D(0,0);
            if (GameData.gameKeys.isDown(node.controlComponent.ULDR[0])) {
                v.add(0,1);
            }
            if (GameData.gameKeys.isDown(node.controlComponent.ULDR[1])) {
                v.add(-1,0);
            }
            if (GameData.gameKeys.isDown(node.controlComponent.ULDR[2])) {
                v.add(0,-1);
            }
            if (GameData.gameKeys.isDown(node.controlComponent.ULDR[3])) {
                v.add(1,0);
            }
            v.normalize();
            if (node.speedComponent != null)
                v.multiply(node.speedComponent.speed);
            node.velocityComponent.velocity.set(v);
            Vector2D mousePos = GameData.toWorldSpace(GameData.gameKeys.getMousePos());
            // get vector from entity position to mouse position
            Vector2D toMousePos = mousePos.getSubtracted(node.positionComponent.position);
            if (node.controlComponent.pointsToMouse) {
                // set rotation based on mouse
                node.directionComponent.dir.set(toMousePos.getNormalized());
            }
            if (node.actionSetComponent != null) {
                if (GameData.gameKeys.isDown(MouseButton.PRIMARY)) {

                    // logic for secondary attack
                    if (node.actionSetComponent.actions.size() > 1 && node.actionSetComponent.lastAction == node.actionSetComponent.actions.get(0) && GameData.getCurrentMillis() < node.actionSetComponent.lastActionTime + node.actionSetComponent.lastAction.duration + 50)
                        performAction(node.actionSetComponent, 1);
                    else if (!node.actionSetComponent.actions.isEmpty())
                        performAction(node.actionSetComponent, 0);
                }
            }
            if (node.throwComponent != null) {
                double d = toMousePos.getLength();
                node.throwComponent.distance = Math.clamp(d, node.throwComponent.min, node.throwComponent.range);
            }

        }
    }

    @Override
    public int getPriority()
    {
        return Priority.PREPROCESSING.get();
    }

    public void performAction(ActionSetComponent acs, int i) {
        if (GameData.getCurrentMillis() <= acs.lastActionTime + acs.lastAction.duration) return;

        long now = GameData.getCurrentMillis();
        acs.lastAction = acs.actions.get(i);
        acs.lastActionTime = now;
    }

}

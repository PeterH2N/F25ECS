package dk.sdu.petni23.controlnode;

import dk.sdu.petni23.common.Engine;
import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.services.IProcessingSystem;
import dk.sdu.petni23.common.util.Vector2D;

public class ControlSystem implements IProcessingSystem
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
            node.velocityComponent.setVelocity(v);

            if (node.controlComponent.pointsToMouse) {
                // set rotation based on mouse
                // get vector from entity position to mouse position
                Vector2D dir = GameData.toWorldSpace(GameData.gameKeys.getMousePos()).getSubtracted(node.positionComponent.getPosition());
                // vector is normalized in the setter
                node.directionComponent.setDirection(dir);
            }

        }
    }

}

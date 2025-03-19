package dk.sdu.petni23.animationnode;


import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;

public class AnimationSystem implements ISystem
{
    @Override
    public void update(double deltaTime)
    {
        long now = GameData.getCurrentMillis();
        for (var node : Engine.getNodes(AnimationNode.class)) {
            node.spriteComponent.animationIndex = 0;
            node.spriteComponent.mirror = false;
            node.spriteComponent.reverse = false;
            if (node.actionSetComponent != null && node.actionSetComponent.isPerformingAction() && node.directionComponent != null) {
                node.spriteComponent.time = now - node.actionSetComponent.lastActionTime;
                node.spriteComponent.animationIndex = node.actionSetComponent.lastAction.animationIndex;

                // direction
                if (node.actionSetComponent.lastAction.biDirectional)
                    node.spriteComponent.mirror = node.directionComponent.getDirection().x < 0;
                else {
                    var dir = node.directionComponent.getDirection();
                    if (dir.x <= 0.5 && dir.x > -0.5 && dir.y > 0) {         // up
                        node.spriteComponent.animationIndex += 2;
                    }
                    else if (dir.x < 0.5 && dir.x >= -0.5 && dir.y < 0) {    // down
                        node.spriteComponent.animationIndex += 1;
                    }
                    else if (dir.x < 0) {    // left
                        node.spriteComponent.mirror = true;
                    }

                }


            }
            else {
                node.spriteComponent.time = now - node.spriteComponent.createdAt;
                node.spriteComponent.animationIndex = 0;
                boolean moving = false;
                // update animation index based on stuff
                if (node.velocityComponent != null)
                    moving = !node.velocityComponent.getVelocity().equals(Vector2D.ZERO);

                // mirror if facing left
                if (node.directionComponent != null)
                    node.spriteComponent.mirror = node.directionComponent.getDirection().x < 0;

                // if facing in the opposite direction of the movement
                if (node.directionComponent != null && node.velocityComponent != null) {
                    var dir = node.directionComponent.getDirection().x > 0;
                    var vel = node.velocityComponent.getVelocity().x > 0;
                    node.spriteComponent.reverse = dir ^ vel;
                }

                if (moving) node.spriteComponent.animationIndex++;
            }
        }
    }

    @Override
    public int getPriority()
    {
        return Priority.PREPROCESSING.get();
    }


}

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
        for (var node : Engine.getNodes(AnimationNode.class)) {
            node.spriteComponent.animationIndex = 0;
            doRegularAnimation(node);
            doActionAnimation(node);
        }
    }

    @Override
    public int getPriority()
    {
        return Priority.PREPROCESSING.get();
    }

    private void doActionAnimation(AnimationNode node) {
        if (node.actionSetComponent == null) return;
        long now = GameData.getCurrentMillis();
        boolean isPerformingAction = now < node.actionSetComponent.lastActionTime + node.actionSetComponent.lastAction.duration;
        if (isPerformingAction) {
            node.spriteComponent.mirror = false;
            node.spriteComponent.reverse = false;
            node.spriteComponent.time = now - node.actionSetComponent.lastActionTime;
            node.spriteComponent.animationIndex = node.actionSetComponent.lastAction.animationIndex;

            if (node.directionComponent != null) {
                // direction
                if (node.actionSetComponent.lastAction.biDirectional)
                    node.spriteComponent.mirror = node.directionComponent.dir.x < 0;
                else {
                    var dir = node.directionComponent.dir;
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
        }
    }

    private void doRegularAnimation(AnimationNode node) {
        long now = GameData.getCurrentMillis();
        node.spriteComponent.time = now - node.spriteComponent.createdAt;
        boolean moving = false;
        // update animation index based on stuff
        if (node.velocityComponent != null)
            moving = !node.velocityComponent.velocity.equals(Vector2D.ZERO);

        // mirror if facing left
        if (node.directionComponent != null)
            node.spriteComponent.mirror = node.directionComponent.dir.x < 0;

        // if facing in the opposite direction of the movement
        if (node.directionComponent != null && node.velocityComponent != null) {
            var dir = node.directionComponent.dir.x > 0;
            var vel = node.velocityComponent.velocity.x > 0;
            node.spriteComponent.reverse = dir ^ vel;
        }

        if (moving) node.spriteComponent.animationIndex++;
    }


}

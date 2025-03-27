package dk.sdu.petni23.animationnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.gameengine.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;

public class AnimationSystem implements ISystem
{
    @Override
    public void update(double deltaTime)
    {
        for (var node : Engine.getNodes(AnimationNode.class)) {
            node.spriteComponent.row = 0;
            doRegularAnimation(node);
            doActionAnimation(node);

            int numFrames = node.spriteComponent.spriteSheet.numFramesArray[node.spriteComponent.row];
            int i = (int)((node.animationComponent.time / 100) % node.spriteComponent.spriteSheet.numFramesArray[node.spriteComponent.row]);
            if (node.animationComponent.reverse) i = (numFrames - 1) - i;
            node.spriteComponent.column = i;
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
            node.animationComponent.reverse = false;
            node.animationComponent.time = now - node.actionSetComponent.lastActionTime;
            node.spriteComponent.row = node.actionSetComponent.lastAction.animationIndex;

            if (node.directionComponent != null) {
                // direction
                var dir = node.directionComponent.dir;
                double dr = Math.abs(dir.getRotatedBy(-Math.PI * 0.5).getAngle());
                int numDirections = node.actionSetComponent.lastAction.directionality.value();
                double angleStep = 2 * Math.PI / numDirections;
                int i = 0;
                for (double r = angleStep * 0.5; r <= Math.PI + angleStep + 0.01; r += angleStep, i++) {
                    if (dr > r - angleStep && dr <= r) {
                        node.spriteComponent.row += i;
                        break;
                    }
                }
            }
        }
    }

    private void doRegularAnimation(AnimationNode node) {
        long now = GameData.getCurrentMillis();
        node.animationComponent.time = now - node.animationComponent.createdAt;
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
            node.animationComponent.reverse = dir ^ vel;
        }

        if (moving) {
            node.spriteComponent.row++;


        }
    }


}

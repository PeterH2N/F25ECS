package dk.sdu.petni23.spritenode;

import dk.sdu.petni23.common.Engine;
import dk.sdu.petni23.common.services.IProcessingSystem;
import dk.sdu.petni23.common.util.Vector2D;

public class SpriteSystem implements IProcessingSystem
{
    @Override
    public void update(double deltaTime)
    {
        for (var node : Engine.getNodes(SpriteNode.class)) {
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

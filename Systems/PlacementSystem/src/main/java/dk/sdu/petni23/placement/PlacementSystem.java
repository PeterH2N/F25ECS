package dk.sdu.petni23.placement;

import dk.sdu.petni23.common.components.BindingComponent;
import dk.sdu.petni23.common.components.PlacementComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.misc.Manifold;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.enums.GameMode;

import java.util.ArrayList;
import java.util.List;

public class PlacementSystem implements ISystem {

    static final List<Entity> placedEntities = new ArrayList<>();
    private final ColorAdjust white = new ColorAdjust(0.0, -0.5, 0.5, 0);
    @Override
    public void update(double deltaTime) {

        // Toggle between placing mode and regular mode when 'C' is pressed - For debug only
        /*if (GameData.gameKeys.isPressed(KeyCode.C)) {
            GameMode newMode = (GameData.getGameMode() != GameMode.PLACING) ? GameMode.PLACING : GameMode.REGULAR;
            GameData.setGameMode(newMode);
        }*/
        
        if (GameData.getGameMode()!=GameMode.PLACING && GameData.getGameMode() != GameMode.REMOVING){
            return;
        }
        if (GameData.getHand() == null) return;

        if (GameData.getGameMode() == GameMode.PLACING) {

            var entity = GameData.getHand();
            var placementComponent = entity.get(PlacementComponent.class);
            if (placementComponent == null) return;
            var positionComponent = entity.get(PositionComponent.class);
            if (positionComponent == null) return;

            var collision = entity.get(CollisionComponent.class);
            boolean isColliding = false;
            // if placing here would collide with other things in world
            for (var m : GameData.world.collisionManifolds) {
                if ((m.aShape == collision.shape || m.bShape == collision.shape) && m.collide) {
                    isColliding = true;
                    break;
                }
            }
            var sprite = entity.get(SpriteComponent.class);
            if (sprite != null) {
                sprite.effect = isColliding ? white : null;
            }


            if (GameData.gameKeys.isDown(MouseButton.PRIMARY) && !isColliding) {
                // Add the collision and hitbox components to the entity, and remove velocity
                collision.active = true;
                for (var component : placementComponent.components.values()) {
                    entity.add(component);
                }
                for (var c : placementComponent.toRemove) {
                    entity.remove(c);
                }
                GameData.setHand(null);
                GameData.setGameMode(GameMode.REGULAR);
            }

            Vector2D mousePos = GameData.gameKeys.getMousePos();
            double mousePosRelX = mousePos.x / GameData.getDisplayWidth();
            double mousePosRelY = mousePos.y / GameData.getDisplayHeight();

            double cameraPosX = GameData.camera.getCenter().x;
            double cameraPosY = GameData.camera.getCenter().y;

            double rx = mousePosRelX * GameData.camera.getWidth() - GameData.camera.getWidth() / 2;
            double ry = mousePosRelY * GameData.camera.getHeight() - GameData.camera.getHeight() / 2;

            double x = Math.floor(cameraPosX + rx);
            double y = Math.floor(cameraPosY + (-1 * ry));

            positionComponent.position.set(new Vector2D(x + 0.5, y));

        }
    }


    @Override
    public int getPriority() {
        return Priority.PROCESSING.get();
    }
}

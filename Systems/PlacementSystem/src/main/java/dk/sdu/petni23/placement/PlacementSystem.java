package dk.sdu.petni23.placement;

import dk.sdu.petni23.common.components.BindingComponent;
import dk.sdu.petni23.common.components.PlacementComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.misc.Manifold;
import dk.sdu.petni23.common.world.GameWorld;
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

        if (GameData.getGameMode() == GameMode.PLACING) {
            // if we press escape, we simply exit placing mode and hand gets reset
            if (GameData.gameKeys.isPressed(KeyCode.ESCAPE)) {
                Engine.removeEntity(GameData.getHand());
                GameData.setHand(null);
                GameData.setGameMode(GameMode.REGULAR);
            }

            var entity = GameData.getHand();
            if (entity == null) return;
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
                if (isColliding) sprite.effects.add(white);
            }


            if (GameData.gameKeys.isPressed(MouseButton.PRIMARY) && !isColliding) {
                // Add the collision and hitbox components to the entity, and remove velocity
                collision.active = true;
                for (var component : placementComponent.components.values()) {
                    entity.add(component);
                }
                for (var c : placementComponent.toRemove) {
                    entity.remove(c);
                }
                GameData.setHand(null);
                GameData.setGameMode(GameMode.SHOPPING);
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

        if (GameData.getGameMode() == GameMode.REMOVING) {
            if (GameData.gameKeys.isPressed(KeyCode.ESCAPE)) {
                GameData.setGameMode(GameMode.SHOPPING);
            }
            // we cant easily get intersection with sprite unfortunately
            // we'll get all the entities that collide with the tile the mouse is currently hovering over
            var mousePos = GameData.gameKeys.getMousePos();
            // to world space, then tile space
            var tilePos = GameWorld.toTileSpace(GameData.toWorldSpace(mousePos));
            System.out.println(tilePos);
            // get all colliders
            var colliders = GameWorld.collisionGrid[(int) tilePos.y][(int) tilePos.x];
            // find any colliders with a placementcomponent
            Entity toRemove = null;
            for (var collider : colliders) {
                Entity r = Engine.getEntity(collider.node.getEntityID());
                if (r.get(PlacementComponent.class) != null) {
                    toRemove = r;
                    break;
                }
            }
            if (toRemove == null) return;

            // set sprite effect
            var sprite = toRemove.get(SpriteComponent.class);
            if (sprite != null) {
                sprite.effects.add(white);
            }

            if (GameData.gameKeys.isPressed(MouseButton.PRIMARY)) {
                Engine.removeEntity(toRemove);
            }

        }
    }


    @Override
    public int getPriority() {
        return Priority.PROCESSING.get();
    }
}

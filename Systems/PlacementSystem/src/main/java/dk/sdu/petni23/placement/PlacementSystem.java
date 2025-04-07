package dk.sdu.petni23.placement;

import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.enums.GameMode;

public class PlacementSystem implements ISystem {

    @Override
    public void update(double deltaTime) {

        // Toggle between placing mode and regular mode when 'C' is pressed
        if (GameData.gameKeys.isPressed(KeyCode.C)) {
            GameMode newMode = (GameData.getGameMode() != GameMode.PLACING) ? GameMode.PLACING : GameMode.REGULAR;
            GameData.setGameMode(newMode);
        }
        
        if (GameData.getGameMode()!=GameMode.PLACING || GameData.getHand() == null){
            return;
        }

        for (PlacementNode node : Engine.getNodes(PlacementNode.class)) {
            Entity entity = Engine.getEntity(node.getEntityID());

            if (entity != GameData.getHand()) {
                continue;
            }

            if (GameData.getGameMode() == GameMode.PLACING) {
                Vector2D mousePos = GameData.gameKeys.getMousePos();
                double mousePosRelX = mousePos.x / GameData.getDisplayWidth();
                double mousePosRelY = mousePos.y / GameData.getDisplayHeight();

                double cameraPosX = GameData.camera.getCenter().x;
                double cameraPosY = GameData.camera.getCenter().y;

                double rx = mousePosRelX * GameData.camera.getWidth() - GameData.camera.getWidth() / 2;
                double ry = mousePosRelY * GameData.camera.getHeight() - GameData.camera.getHeight() / 2;

                double x = cameraPosX + rx;
                double y = cameraPosY + (-1 * ry);

                node.positionComponent.position.set(new Vector2D(x, y));

                if (GameData.gameKeys.isDown(MouseButton.PRIMARY)) {
                    // Add the collision and hitbox components to the entity
                    entity.add(node.placementComponent.collisionComponent);
                    entity.add(node.placementComponent.hitBoxComponent);
                    GameData.setHand(null);
                    GameData.setGameMode(GameMode.REGULAR);
                }
            }
        }
    }

    @Override
    public int getPriority() {
        return Priority.PROCESSING.get();
    }
}

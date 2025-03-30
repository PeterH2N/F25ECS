package dk.sdu.petni23.placement;

import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.enums.GameMode;
import dk.sdu.petni23.common.util.Vector2D;

public class PlacementSystem implements ISystem{

    @Override
    public void update(double deltaTime) {
        for(PlacementNode node : Engine.getNodes(PlacementNode.class)){
            if(Engine.getEntity(node.getEntityID())!=GameData.getHand()){
                continue;
            }
            if(GameData.gameKeys.isDown(KeyCode.C)){
                GameMode newMode = (GameData.getGameMode() == GameMode.REGULAR) ? GameMode.PLACING : GameMode.REGULAR;
                GameData.setGameMode(newMode);
            }
            if(GameData.getGameMode() == GameMode.PLACING){

                Vector2D mousePos = GameData.gameKeys.getMousePos();
                double mousePosRelX = mousePos.x/GameData.getDisplayWidth();
                double mousePosRelY = mousePos.y/GameData.getDisplayHeight();
                
                double cameraPosX = GameData.camera.getCenter().x;
                double cameraPosY = GameData.camera.getCenter().y;

                double x = cameraPosX;
                double y = cameraPosY;
               
                double rx = mousePosRelX * GameData.camera.getWidth() - GameData.camera.getWidth() / 2;
                double ry = mousePosRelY * GameData.camera.getHeight() - GameData.camera.getHeight() / 2;

                x= x+rx;
                y= (y+(-1*ry));
                node.positionComponent.position.set(new Vector2D(x,y));
                if(GameData.gameKeys.isDown(MouseButton.PRIMARY)){
                    GameData.setHand(null);
                }
            }
        }
    }
    @Override
    public int getPriority(){
        return Priority.PROCESSING.get();
    }
    
}
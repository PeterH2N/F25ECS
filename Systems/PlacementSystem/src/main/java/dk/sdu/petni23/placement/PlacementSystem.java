package dk.sdu.petni23.placement;

import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPhysicsSystem;
import dk.sdu.petni23.gameengine.services.ISystem;
import javafx.scene.input.MouseButton;

import java.lang.reflect.Method;

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
            if(GameData.gameKeys.isDown(node.placementComponent.C)){
                GameMode newMode = (GameData.getGameMode() == GameMode.REGULAR) ? GameMode.PLACING : GameMode.REGULAR;
                GameData.setGameMode(newMode);
            }
            if(GameData.getGameMode() == GameMode.PLACING){
                
                Vector2D mousePos = GameData.gameKeys.getMousePos();
                
                double x = (mousePos.x/GameData.getDisplayWidth()) * GameData.worldSize - (double) GameData.worldSize / 2;
                double y = -1*((mousePos.y/GameData.getDisplayWidth()) * GameData.worldSize - (double) GameData.worldSize / 2);

                //adjust for zoom factor
                double z_width = GameData.camera.getWidth()/GameData.worldSize;
                x = z_width * x;
                y = z_width * y;
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
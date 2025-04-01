package dk.sdu.petni23.placement;

import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.gameengine.util.Vector2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.enums.GameMode;

public class PlacementSystem implements ISystem{

    Entity entity;
    
    @Override
    public void update(double deltaTime) {
        for(PlacementNode node : Engine.getNodes(PlacementNode.class)){
            entity = Engine.getEntity(node.getEntityID());
            if(entity!=GameData.getHand()){
                continue;
            }
            if(GameData.gameKeys.isPressed(KeyCode.C)){
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
                    entity.add(new CollisionComponent(node.placementComponent.collisionShape));
                    entity.add(new HitBoxComponent(node.placementComponent.hitBoxShape));
                    Engine.removeEntity(entity);
                    Engine.addEntity(entity);
                    GameData.setHand(null);
                }else{

                }
            }
        }
    }
    @Override
    public int getPriority(){
        return Priority.PROCESSING.get();
    }
    
}
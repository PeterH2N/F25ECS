package dk.sdu.petni23.shopping;

import dk.sdu.petni23.common.components.PlacementComponent;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.enums.MouseMode;

public class ShopSystem implements ISystem,IPluginService {
    private static AnchorPane pane;
    private Vector2D shopPos;
    private ShopNode shopNode;
    private boolean perimeter;
    private static ShopController controller;

    @Override
    public void update(double deltaTime) {
        perimeter = withinPerimeter();

        for (ShopNode node : Engine.getNodes(ShopNode.class)){
            shopNode = node;
        }
        if(shopNode == null){
            return;
        }
        if(GameData.gameKeys.isPressed(KeyCode.B) && GameData.getMouseMode() != MouseMode.PLACING){
            MouseMode newMode = (GameData.getMouseMode() != MouseMode.SHOPPING) ? MouseMode.SHOPPING : MouseMode.REGULAR;
            GameData.setMouseMode(newMode);
        }

        if(GameData.getMouseMode()!=MouseMode.SHOPPING || !perimeter){
            if(shopNode.shopComponent.visible){
                shopNode.shopComponent.visible = false;
                GameData.gameWindow.getChildren().remove(pane);
            }
            return;
        }
        if(!shopNode.shopComponent.visible){
            shopNode.shopComponent.visible = true;
            GameData.gameWindow.getChildren().add(pane);
        }
    }

    @Override
    public int getPriority() {
        return Priority.PROCESSING.get();
    }

    private boolean withinPerimeter(){

        //needs implemenntation
        return true;
    }

    @Override
    public void start() {
        
        URL url = getClass().getResource("/shop.fxml");
        if(url == null){
            System.out.println("shop FXML file not found");
        }

        try {
            FXMLLoader loader = new FXMLLoader(url);
            pane = loader.load();
            StackPane.setAlignment(pane, Pos.BOTTOM_CENTER);
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void stop() {
        
    }
    
}

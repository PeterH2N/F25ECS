package dk.sdu.petni23.shopping;

import dk.sdu.petni23.common.components.PlacementComponent;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.enums.MouseMode;

public class ShopSystem implements ISystem,IPluginService {
    private static AnchorPane pane;
    private ShopNode shopNode;
    private static ShopController controller;

    private static final Image selected = new Image(Objects.requireNonNull(ShopSystem.class.getResourceAsStream("/shop/Frame.png")));

    @Override
    public void update(double deltaTime) {

        for (ShopNode node : Engine.getNodes(ShopNode.class)){
            shopNode = node;
        }
        if(shopNode == null){
            return;
        }
        if(GameData.gameKeys.isPressed(KeyCode.B) && GameData.getMouseMode() != MouseMode.PLACING){
            shopNode.shopComponent.visible = !shopNode.shopComponent.visible;
            GameData.setMouseMode(MouseMode.REGULAR);
            if (shopNode.shopComponent.visible) GameData.gameWindow.getChildren().add(pane);
            else GameData.gameWindow.getChildren().remove(pane);
        }
        if (GameData.getMouseMode() == MouseMode.REGULAR) {
            if (controller.currentChoice != null) {
                controller.currentChoice.setImage(null);
                controller.currentChoice = null;
            }
        } else {
            if (controller.currentChoice != null) {
                controller.frames.forEach(imageView -> imageView.setImage(null));
                controller.currentChoice.setImage(selected);
            }
        }

    }

    @Override
    public int getPriority() {
        return Priority.PROCESSING.get();
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

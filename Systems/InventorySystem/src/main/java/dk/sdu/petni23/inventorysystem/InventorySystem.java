package dk.sdu.petni23.inventorysystem;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.ControlComponent;
import dk.sdu.petni23.common.components.inventory.InventoryComponent;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.IRenderSystem;
import dk.sdu.petni23.gameengine.services.ISystem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class InventorySystem implements ISystem, IPluginService {
    static AnchorPane pane;
    InventoryNode playerInventory;
    static InventoryController playerController;

    @Override
    public void update(double deltaTime) {
        setPlayerInventory();

        if (playerInventory != null) {
            // Toggle by key (E)
            if (GameData.gameKeys.isPressed(KeyCode.E)) {
                if (!playerInventory.inventoryComponent.visible) {
                    playerInventory.inventoryComponent.visible = true;
                    GameData.gameWindow.getChildren().add(pane);
                } else {
                    playerInventory.inventoryComponent.visible = false;
                    GameData.gameWindow.getChildren().remove(pane);
                }
            }

            updateInventoryController(playerController, playerInventory);
        }
    }

    @Override
    public int getPriority() {
        return Priority.PROCESSING.get();
    }

    void updateInventoryController(InventoryController controller, InventoryNode inventory) {
        if (inventory.walletComponent != null) {
            // Update inventory values on the controller
            controller.updateInventoryValues(
                inventory.inventoryComponent.meat,   // Meat count
                inventory.inventoryComponent.wood,   // Wood count
                inventory.walletComponent.money      // Gold count
            );
        }
    }

    void setPlayerInventory() {
        if (playerInventory != null)
            return;
        for (var node : Engine.getNodes(InventoryNode.class)) {
            if (Engine.getEntity(node.getEntityID()).get(ControlComponent.class) != null) {
                playerInventory = node;
                break;
            }
        }
    }

    @Override
    public void start() {
        URL fxmlLocation = getClass().getResource("/Inventory.fxml");

        if (fxmlLocation == null) {
            System.out.println("🚨 FXML file NOT FOUND: ui/Inventory.fxml");
            System.out.println("🔎 Listing all resources in classpath:");

            try {
                Enumeration<URL> resources = getClass().getClassLoader().getResources("");
                while (resources.hasMoreElements()) {
                    System.out.println("📂 " + resources.nextElement());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.err.println("Cannot load FXML file: ui/Inventory.fxml");
        } else {
            System.out.println("✅ FXML file found at: " + fxmlLocation);
        }

        try {
            assert fxmlLocation != null;
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            pane = loader.load();
            StackPane.setAlignment(pane, Pos.TOP_LEFT);
            playerController = (InventoryController) loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
    }
}

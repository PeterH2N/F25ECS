package dk.sdu.petni23.debugnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.DebugOptions;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class DebugSystem implements ISystem, IPluginService
{
    private static Stage debugStage;
    @Override
    public void update(double deltaTime)
    {
        // toggle mode
        if (GameData.gameKeys.isDown(KeyCode.CONTROL) && GameData.gameKeys.isPressed(KeyCode.P)) {
            toggleDebug();
        }

    }

    void toggleDebug() {
        DebugOptions options = GameData.debugOptions;
        if (!options.active) {
            options.active = true;
            debugStage.show();
            GameData.stage.requestFocus();
        } else {
            options.active = false;
            debugStage.close();
        }
    }

    @Override
    public int getPriority()
    {
        return Priority.PREPROCESSING.get();
    }


    @Override
    public void start()
    {
        URL fxmlLocation = getClass().getResource("/DebugWindow.fxml");

        if (fxmlLocation == null) {
            System.out.println("🚨 FXML file NOT FOUND: ui/DebugWindow.fxml");
            System.out.println("🔎 Listing all resources in classpath:");

            try {
                Enumeration<URL> resources = getClass().getClassLoader().getResources("");
                while (resources.hasMoreElements()) {
                    System.out.println("📂 " + resources.nextElement());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.err.println("Cannot load FXML file: ui/DebugWindow.fxml");
        } else {
            System.out.println("✅ FXML file found at: " + fxmlLocation);
        }

        try {
            assert fxmlLocation != null;
            Parent root = FXMLLoader.load(fxmlLocation);
            debugStage = new Stage();
            var scene = new Scene(root);
            debugStage.setScene(scene);
            debugStage.setOnCloseRequest(windowEvent -> GameData.debugOptions.active = false);
            debugStage.setOnShown(windowEvent -> {
                debugStage.setX(GameData.stage.getX() - debugStage.getWidth() - 10);
                debugStage.setY(GameData.stage.getY());
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void stop()
    {

    }
}



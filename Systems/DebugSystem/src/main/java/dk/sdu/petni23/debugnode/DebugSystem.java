package dk.sdu.petni23.debugnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.DebugOptions;
import dk.sdu.petni23.debugnode.ui.DebugWindowController;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;

public class DebugSystem extends ISystem implements IPluginService
{
    private static Stage debugStage;
    private static DebugWindowController controller;
    @Override
    public void update(double deltaTime)
    {
        // toggle mode
        if (GameData.gameKeys.isDown(KeyCode.CONTROL) && GameData.gameKeys.isPressed(KeyCode.P)) {
            toggleDebug();
        }
        if (GameData.debugOptions.active) {
            updateSystemMonitor();
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

    void updateSystemMonitor() {
        // initialize system performance monitor
        var systems = new ArrayList<>(Engine.getSystems());
        systems.sort(Comparator.comparingLong(ISystem::getAvgDuration).reversed());
        controller.systemsVBox.getChildren().clear();
        for (ISystem system : systems) {
            String name = system.getClass().getName();
            var hBox = new HBox();
            hBox.setSpacing(5);
            controller.systemsVBox.getChildren().add(hBox);
            hBox.getChildren().add(new Text(name));
            Text durationText = new Text(String.valueOf((double) system.getAvgDuration() / 1000000));
            hBox.getChildren().add(durationText);
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
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            debugStage = new Stage();
            var scene = new Scene(root);
            debugStage.setScene(scene);
            debugStage.setOnCloseRequest(windowEvent -> GameData.debugOptions.active = false);
            debugStage.setOnShown(windowEvent -> {
                debugStage.setX(GameData.stage.getX() - debugStage.getWidth() - 10);
                debugStage.setY(GameData.stage.getY());
            });
            controller = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void stop()
    {

    }
}



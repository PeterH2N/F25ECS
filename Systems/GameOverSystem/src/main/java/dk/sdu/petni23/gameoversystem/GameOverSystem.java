package dk.sdu.petni23.gameoversystem;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class GameOverSystem extends ISystem {
    static AnchorPane gameOverPane;

    @Override
    public void update(double deltaTime) {
        for (var node : Engine.getNodes(GameOverNode.class)) {
            if (!node.gameOverComponent.triggered)
                continue;

            System.out.println("💀 Game over triggered by GameOverSystem");

            // Prevent duplicate triggers
            node.gameOverComponent.triggered = false;

            // Show Game Over UI
            Platform.runLater(() -> {
                try {
                    URL fxmlLocation = getClass().getResource("/ui/GameOver.fxml");

                    if (fxmlLocation == null) {
                        System.err.println("🚨 FXML file NOT FOUND: /ui/GameOver.fxml");
                        return;
                    }

                    FXMLLoader loader = new FXMLLoader(fxmlLocation);
                    gameOverPane = loader.load();
                    StackPane.setAlignment(gameOverPane, Pos.CENTER);

                    GameData.gameWindow.getChildren().add(gameOverPane);

                    GameData.setPaused(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public int getPriority() {
        return Priority.PROCESSING.get();
    }
}

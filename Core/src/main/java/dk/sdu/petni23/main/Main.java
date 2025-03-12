package dk.sdu.petni23.main;

import dk.sdu.petni23.common.Engine;
import dk.sdu.petni23.common.data.GameData;
import dk.sdu.petni23.common.services.ISystem;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import static dk.sdu.petni23.common.data.GameData.gameWindow;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        Scene scene = new Scene(gameWindow);
        gameWindow.setPrefSize(800,800);
        GameData.displayWidthProperty().bind(gameWindow.widthProperty());
        GameData.displayHeightProperty().bind(gameWindow.heightProperty());

        Engine.start();

        render();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //gameData.setTime(now);
                //if (!gameData.isPaused())
                    Engine.update(1.0/60);
                //gameData.getKeys().update();
                //gameData.setFrameTime(java.lang.System.nanoTime() - now);
            }
        }.start();
    }
}
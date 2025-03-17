package dk.sdu.petni23.main;

import dk.sdu.petni23.common.Engine;
import dk.sdu.petni23.common.GameData;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static dk.sdu.petni23.common.GameData.gameWindow;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        Scene scene = GameData.scene;
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
                GameData.setTime(now);
                Engine.update(GameData.getDeltaTime());
                GameData.setFrameTime(java.lang.System.nanoTime() - now);
            }
        }.start();
    }
}
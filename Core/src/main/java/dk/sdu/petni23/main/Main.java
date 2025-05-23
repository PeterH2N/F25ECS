package dk.sdu.petni23.main;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.sound.SoundEffect;
import dk.sdu.petni23.gameengine.Engine;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        GameData.gameWindow.setPrefSize(800, 800);
        GameData.stage = stage;
        stage.setMinWidth(200);
        stage.setMinHeight(200);
        GameData.getFocusedProperty().bind(stage.focusedProperty());
        stage.setOnCloseRequest(windowEvent -> System.exit(0));

        SoundEffect.init();
        Engine.start();

        GameData.setPaused(false);

        render();
        stage.setTitle("Tiny Swords");
        stage.setScene(GameData.scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Engine.stop();
        super.stop();
        System.exit(0);
    }

    private void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                GameData.setTime(now);
                if (!GameData.isPaused())
                    Engine.update(GameData.getDeltaTime());
                GameData.setFrameTime(System.nanoTime() - now);
            }
        }.start();
    }
}

package dk.sdu.petni23.gameflow.ui;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.enums.GameMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Objects;

public class RoundButton extends StackPane {
    static Image button = (new Image(
            Objects.requireNonNull(RoundButton.class.getResourceAsStream("/buttonimages/Button_Blue_3Slides.png"))));
    static Image buttonPressed = new Image(Objects
            .requireNonNull(RoundButton.class.getResourceAsStream("/buttonimages/Button_Blue_3Slides_Pressed.png")));

    ImageView background = new ImageView(button);
    Text text = new Text("Start");

    public RoundButton() {
        background.setFitWidth(100);
        getChildren().add(background);
        getChildren().add(text);
        setOnMousePressed(mouseEvent -> background.setImage(buttonPressed));
        setOnMouseClicked(mouseEvent -> {
            if (GameData.getGameMode() == GameMode.WAIT) {
                GameData.setGameMode(GameMode.STARTING);
            }
        });
        text.setFont(GameData.logFont);
        text.setFill(Color.WHITE);

        GameData.onGameModeChanged = gameMode -> {
            if (gameMode == GameMode.WAIT) {
                setDisable(false);
                background.setImage(button);
                text.setText("Start");
            } else if (gameMode == GameMode.STARTING) {
                setDisable(true);
                background.setImage(buttonPressed);
                text.setText("Going");
            }
        };
    }
}

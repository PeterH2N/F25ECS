package dk.sdu.petni23.gameflow.ui;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.score.ScoreManager;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class ScoreUIController {

    RoundButton roundButton = new RoundButton();

    @FXML
    private Text scoreText;
    @FXML
    private HBox hBox;
    @FXML
    private ImageView ribbonImage;

    @FXML
    public void initialize() {
        ScoreManager.setScoreListener(score -> scoreText.setText("Score " + score));
        scoreText.setFont(GameData.logFont);
        scoreText.setMouseTransparent(true);
        ribbonImage.setImage(new Image(Objects.requireNonNull(ScoreUIController.class.getResourceAsStream("/buttonimages/Ribbon_Red_3Slides.png"))));
        ribbonImage.setFitWidth(300);
        ribbonImage.setFitHeight(50);
        hBox.getChildren().add(roundButton);
    }
}

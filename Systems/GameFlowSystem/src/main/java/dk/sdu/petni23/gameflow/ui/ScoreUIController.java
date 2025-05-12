package dk.sdu.petni23.gameflow.ui;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.score.ScoreManager;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ScoreUIController {

    @FXML
    private Text scoreText;

    @FXML
    public void initialize() {
        ScoreManager.setScoreListener(score -> scoreText.setText("Score: " + score));
        scoreText.setFont(GameData.logFont);
    }
}

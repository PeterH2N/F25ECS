package dk.sdu.petni23.gameoversystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import dk.sdu.petni23.ui.StartMenu;

public class GameOverController {

    @FXML
    private void onQuit(ActionEvent event) {
        try {
            // Luk nuværende spilvindue
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            // 🧼 Nulstil spillet
            dk.sdu.petni23.gameengine.Engine.clear();
            dk.sdu.petni23.common.GameData.gameWindow.getChildren().clear();

            // 🔄 Genstart startmenuen
            StartMenu newMenu = new StartMenu();
            newMenu.start(new Stage());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

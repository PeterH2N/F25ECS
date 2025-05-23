package dk.sdu.petni23.gameoversystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.ui.StartMenu;

public class GameOverController {

    @FXML
    private void onQuit(ActionEvent event) {
        try {
            // Luk nuværende spilvindue
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();


            Engine.stop();
            
            Engine.getEntities().forEach(Engine::removeEntity);
            GameData.world.map.genMap();

            StartMenu newMenu = new StartMenu();
            newMenu.start(new Stage());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

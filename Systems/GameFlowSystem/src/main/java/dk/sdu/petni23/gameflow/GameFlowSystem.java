package dk.sdu.petni23.gameflow;

import java.net.URL;
import java.util.ArrayList;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.enums.GameMode;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class GameFlowSystem implements ISystem,IPluginService {

    private static AnchorPane pane;

    ArrayList<IEntitySPI> placeableEntities;
    private GameFlowController controller = new GameFlowController();
    private final long interval = 10000;
    private long roundEndTime = System.currentTimeMillis();

    @Override
    public void update(double deltaTime) {
        if(GameData.getGameMode() == GameMode.WAIT){
            if(System.currentTimeMillis()-roundEndTime>interval){
                GameData.setGameMode(GameMode.IN_GAME);
                controller.newRound();
                controller.setScore("Enemies remaining: " + Integer.toString(controller.getCurrentEnemies()));
            }else{
                //controller.setScore(Long.toString(System.currentTimeMillis()-roundEndTime));
            }
        }else if(controller.endRoundIfAppropriate() && GameData.getGameMode() == GameMode.IN_GAME){
            GameData.setGameMode(GameMode.WAIT);
            roundEndTime = System.currentTimeMillis();
        }

    }


    @Override
    public int getPriority() {
        return Priority.PROCESSING.get();
    }


    @Override
    public void start() {
        /* 
        URL url = getClass().getResource("/game_flow.fxml");
        if(url == null){
            System.out.println("FXML file for game flow not found...");
        }

        try {
            FXMLLoader loader = new FXMLLoader(url);
            pane = loader.load();
            controller = loader.getController();
            StackPane.setAlignment(pane, Pos.TOP_RIGHT);
            GameData.gameWindow.getChildren().add(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public void stop() {

    }
}

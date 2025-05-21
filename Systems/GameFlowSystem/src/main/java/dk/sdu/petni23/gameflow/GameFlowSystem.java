package dk.sdu.petni23.gameflow;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.configreader.ConfigReader;
import dk.sdu.petni23.common.enums.GameMode;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameFlowSystem extends ISystem implements IPluginService {
    private static AnchorPane pane;
    private GameFlowController controller = new GameFlowController();
    private final long interval = 10000;
    private long roundEndTime = System.currentTimeMillis();
    public static GameFlow settings;

    @Override
    public void update(double deltaTime) {
        if(GameData.getGameMode() == GameMode.WAIT){
            //System.out.println("Between rounds: " + Long.toString(System.currentTimeMillis()-roundEndTime));
            if(System.currentTimeMillis()-roundEndTime>interval){
                GameData.setGameMode(GameMode.IN_GAME);
                controller.newRound();
            }else{
                //potentially update global time
            }
        }else if(controller.endRoundIfAppropriate() && GameData.getGameMode() == GameMode.IN_GAME){
            GameData.setGameMode(GameMode.WAIT);
            roundEndTime = System.currentTimeMillis();
        }

    }


    @Override
    public int getPriority() {
        return Priority.PREPROCESSING.get();
    }

    @Override
    public void start() {
        InputStream in = ConfigReader.getConfigInputStream();
        try(InputStreamReader inputStreamReader = new InputStreamReader(in)) {
            JsonObject root = JsonParser.parseReader(inputStreamReader).getAsJsonObject();
            JsonElement gameFlowElement = root.get("gameFlowSettings");
            Gson gson = new Gson();
            settings = gson.fromJson(gameFlowElement, GameFlow.class);
        } catch(IOException e) {
            e.printStackTrace();;
        }
        try {
            FXMLLoader loader = new FXMLLoader(GameFlowSystem.class.getResource("/ScoreUI.fxml"));
            pane = loader.load();
            StackPane.setAlignment(pane, Pos.TOP_RIGHT);
            GameData.gameWindow.getChildren().add(pane);
        } catch (IOException e) {
            System.err.println("Failed to load Score UI: " + e.getMessage());
        }
    }

    @Override
    public void stop() {

    }
}

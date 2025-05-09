package dk.sdu.petni23.gameflow;

import java.util.ArrayList;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.enums.GameMode;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.ISystem;

public class GameFlowSystem implements ISystem {

    ArrayList<IEntitySPI> placeableEntities;
    private GameFlowController controller = new GameFlowController();
    private final long interval = 10000;
    private long roundEndTime = System.currentTimeMillis();

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
}

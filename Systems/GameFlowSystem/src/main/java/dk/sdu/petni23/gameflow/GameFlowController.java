package dk.sdu.petni23.gameflow;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import dk.sdu.petni23.common.components.gameflow.SpawnComponent;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import dk.sdu.petni23.common.ISpawn;

public class GameFlowController {

    @FXML
    Text score;

    private ArrayList<ISpawn> services;
    private ArrayList<Vector2D> spawnPoints;
    private double roundIndex = 0;
    private double initialPopulation = 4;
    private double growthRate = 0.5;
    private double populationLimit = 100;

    @FXML
    public void initialize(){
        this.score.setText("none");
    }

    public void setScore(String text){
        System.out.println("called set_score");
        if(score!=null){
            System.out.println("score not null");
            this.score.setText(text);
        }
    }


    private void initRound(){
        services = getServices(ISpawn.class);
        spawnPoints = getSpawnPoints();
    }

    public int getCurrentEnemies(){
        int amount = 0;
        for(SpawnNode n : Engine.getNodes(SpawnNode.class)){
            if(!n.spawnComponent.sustainableAfterRoundEnd){
                amount++;
            }
        }
        return amount;
    }

    public boolean endRoundIfAppropriate(){
        if(getCurrentEnemies()>0){
            return false;
        }
        roundIndex++;
        return true;
    }

    public void newRound(){
        initRound();
        spawnEntites(roundIndex);
    }
    
    private void spawnEntites(double roundIndex){
        int entities = (int)calculatePopulation(roundIndex);
        int j = 0;
        for(ISpawn s : services){
            for (int i = 0;i<entities;i++){
                if(j!=0){
                    j = j % spawnPoints.size();
                }
                s.start(spawnPoints.get(j));
                j++;
            }
        }
    }

    private ArrayList<Vector2D> getSpawnPoints(){
        List<SpawnNode> nodes = Engine.getNodes(SpawnNode.class);
        ArrayList<Vector2D> spawnPoints = new ArrayList<>();
        for (SpawnNode node : nodes){
            if(node.getComponent(SpawnComponent.class).spawnPoint){
                spawnPoints.add(node.getComponent(SpawnComponent.class).pos);
            }
        }
        return spawnPoints;
    }
    private <T> ArrayList<T> getServices(Class<T> c) {
        return new ArrayList<>(ServiceLoader.load(c).stream().map(ServiceLoader.Provider::get).toList()) ;
    }

    private double calculatePopulation(double index){
        return Math.floor(populationLimit/(1+((populationLimit-initialPopulation)/initialPopulation)*Math.pow(Math.E, -growthRate*index)));
    }
}

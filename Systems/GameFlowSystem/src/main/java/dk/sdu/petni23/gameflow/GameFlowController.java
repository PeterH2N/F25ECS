package dk.sdu.petni23.gameflow;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import dk.sdu.petni23.common.components.gameflow.SpawnComponent;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.common.ISpawn;
import dk.sdu.petni23.gameflow.GameFlowSystem;

public class GameFlowController {

    private ArrayList<ISpawn> services;
    private ArrayList<Vector2D> spawnPoints;
    private double roundIndex = 0;
    private void initRound(){
        services = getServices(ISpawn.class);
        spawnPoints = getSpawnPoints();
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
        spawnEntites();
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

    private void spawnEntites(){
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
        return Math.floor(GameFlowSystem.settings.populationLimit/(1+((GameFlowSystem.settings.populationLimit-GameFlowSystem.settings.initialPopulation)/GameFlowSystem.settings.initialPopulation)*Math.pow(Math.E, -GameFlowSystem.settings.growthRate*index)));
    }
}

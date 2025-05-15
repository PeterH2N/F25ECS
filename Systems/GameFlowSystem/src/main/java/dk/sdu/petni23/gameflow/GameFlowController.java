package dk.sdu.petni23.gameflow;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import dk.sdu.petni23.common.components.gameflow.SpawnComponent;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.common.ISpawn;

public class GameFlowController {

    private ArrayList<ISpawn> services;
    private ArrayList<Vector2D> spawnPoints;
    private void initRound(){
        services = getServices(ISpawn.class);
        spawnPoints = getSpawnPoints();
    }

    public boolean endRoundIfAppropriate(){
        for (SpawnNode n : Engine.getNodes(SpawnNode.class)){
            if(!n.spawnComponent.sustainableAfterRoundEnd){
                return false;
            }
        }
        return true;
    }

    public void newRound(){
        initRound();
        spawnEntites();
    }

    private void spawnEntites(){
        for (ISpawn s : services){
            s.start(spawnPoints,1);
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
        //System.out.println(spawnPoints);
        return spawnPoints;
    }
    private <T> ArrayList<T> getServices(Class<T> c) {
        return new ArrayList<>(ServiceLoader.load(c).stream().map(ServiceLoader.Provider::get).toList()) ;
    }
}

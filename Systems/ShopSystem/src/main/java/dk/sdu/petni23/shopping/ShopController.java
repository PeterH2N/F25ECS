package dk.sdu.petni23.shopping;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.util.Map;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.configreader.ConfigReader;
import dk.sdu.petni23.common.enums.GameMode;
import dk.sdu.petni23.common.enums.ShopItems;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.inventorysystem.InventoryNode;
import dk.sdu.petni23.structures.wall.Wall;

public class ShopController {

    InventoryNode inventoryNode;
    ShopNode shopNode;
    ShopItems shopItem;

    @FXML 
    private AnchorPane pane;
    @FXML
    private Button button;

    @FXML
    public void handlePurchaseClick(ActionEvent e){
        for (InventoryNode node : Engine.getNodes(InventoryNode.class)){
            inventoryNode = node;
        }
        for (ShopNode node : Engine.getNodes(ShopNode.class)){
            shopNode = node;
        }
        Button btn = (Button) e.getSource();
        switch(btn.getId()){
            case "stone_wall":
                Map<IEntitySPI.Type,Integer> res = ConfigReader.getItemPrices("stoneWall");
                if(debit(res)){
                    Entity entity = Wall.create(new Vector2D(0,0));
                    Engine.addEntity(entity);
                    GameData.setHand(entity);
                    GameData.setGameMode(GameMode.PLACING);
                }
                break;
            case "wooden_wall":
                break;
            default:
                return;
        }
    }

    private boolean debit(Map<IEntitySPI.Type,Integer> prices){
        Map<IEntitySPI.Type,Integer> inventoryAmounts = inventoryNode.inventoryComponent.amounts;
        for(IEntitySPI.Type resource : prices.keySet()){
            if(inventoryAmounts.get(resource)==null){
                System.out.println("No such resource in inventory");
                return false;
            }
            if(inventoryAmounts.get(resource)<prices.get(resource)){
                System.out.println("Insufficient resources");
                return false;
            }
        }
        for(IEntitySPI.Type resource : prices.keySet()){
            inventoryAmounts.put(resource,inventoryAmounts.get(resource)-prices.get(resource));
        }
        return true;
    }
}

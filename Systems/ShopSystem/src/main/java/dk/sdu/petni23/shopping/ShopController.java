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
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.inventorysystem.InventoryNode;
import dk.sdu.petni23.shopping.ShopItems;
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
                int goldPrice = ConfigReader.getItemPrices("stoneWall", "gold");
                int woodPrice = ConfigReader.getItemPrices("stoneWall", "wood");
                if(!debit(goldPrice, woodPrice)){
                    System.out.println("Insufficient funds.");
                }
                break;
            case "wooden_wall":
                this.shopItem = ShopItems.WOODEN_WALL;
                break;
            default:
                return;
        }
    }

    private boolean debit(int goldPrice,int woodPrice){
        Map<IEntitySPI.Type,Integer> amounts = inventoryNode.inventoryComponent.amounts;
        if(amounts.get(IEntitySPI.Type.GOLD) < goldPrice || amounts.get(IEntitySPI.Type.WOOD) < woodPrice){
            return false;
        }else{
            amounts.put(IEntitySPI.Type.GOLD,amounts.get(IEntitySPI.Type.GOLD)-goldPrice);
            amounts.put(IEntitySPI.Type.WOOD,amounts.get(IEntitySPI.Type.WOOD)-woodPrice);
            Entity wall = Wall.create(new Vector2D(0,0));
            Engine.addEntity(wall);
            GameData.setHand(wall);
            GameData.setGameMode(GameMode.PLACING);
            return true;
        }
    }
}

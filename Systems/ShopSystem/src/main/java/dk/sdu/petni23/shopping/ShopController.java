package dk.sdu.petni23.shopping;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Map;
import java.util.ServiceLoader;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.configreader.ConfigReader;
import dk.sdu.petni23.common.enums.GameMode;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.entity.IEntitySPI.Type;
import dk.sdu.petni23.inventorysystem.InventoryNode;

public class ShopController {

    InventoryNode inventoryNode;
    ShopNode shopNode;
    ArrayList<IEntitySPI> placeableEntities;

    @FXML
    private Text alert;
    @FXML 
    private AnchorPane shop_background;
    @FXML
    private ImageView stone_wall_img;
    @FXML
    private ImageView wooden_wall_img;





    @FXML
    public void initialize() {
        stone_wall_img.setImage(new Image(getClass().getResourceAsStream("/images/stone_wall_shop.png")));
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/shop_background.png"));
        BackgroundImage backgroud = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER, new BackgroundSize(
                150, 150, false, false, true, false
            ));
            shop_background.setBackground(new Background(backgroud));
        placeableEntities = getServices(IEntitySPI.class);

    }





    @FXML
    public void handlePurchaseClick(ActionEvent e){
        for (InventoryNode node : Engine.getNodes(InventoryNode.class)){
            inventoryNode = node;
        }
        for (ShopNode node : Engine.getNodes(ShopNode.class)){
            shopNode = node;
        }
        Button btn = (Button) e.getSource();
        Entity entity = null;
        switch(btn.getId()){
            case "stone_wall_btn":
                System.out.println("stone wall purchse innit");
                if(debit(ConfigReader.getItemPrices(Type.STONE_WALL.getValue())))
                entity = purchase(Type.STONE_WALL);
                break;
            default:
                return;
        }
        if(entity != null){
            Engine.addEntity(entity);
            GameData.setHand(entity);
            GameData.setGameMode(GameMode.PLACING);
        }
    }





    private Entity purchase(Type type){
        for(IEntitySPI spi : placeableEntities){
            if(spi.getType() == type){
                return spi.create(Engine.getEntity(shopNode.getEntityID()));
            }
        }
        throw new RuntimeException("No such type in spis");

    }





    private boolean debit(Map<IEntitySPI.Type,Integer> prices){
        Map<IEntitySPI.Type,Integer> inventoryAmounts = inventoryNode.inventoryComponent.amounts;
        for(IEntitySPI.Type resource : prices.keySet()){
            if(inventoryAmounts.get(resource)==null){
                System.out.println("No such resource in inventory");
                alert.setText("Insufficient resources");
                return false;
            }
            if(inventoryAmounts.get(resource)<prices.get(resource)){
                System.out.println("Insufficient resources");
                alert.setText("Insufficient resources");
                return false;
            }
        }
        for(IEntitySPI.Type resource : prices.keySet()){
            inventoryAmounts.put(resource,inventoryAmounts.get(resource)-prices.get(resource));
        }
        alert.setText("");
        return true;
    }





    private <T> ArrayList<T> getServices(Class<T> c) {
        return new ArrayList<>(ServiceLoader.load(c).stream().map(ServiceLoader.Provider::get).toList()) ;
    }





}

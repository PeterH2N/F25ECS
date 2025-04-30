package dk.sdu.petni23.shopping;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.configreader.ConfigReader;
import dk.sdu.petni23.common.enums.MouseMode;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.entity.IEntitySPI.Type;
import dk.sdu.petni23.inventorysystem.InventoryNode;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.*;

public class ShopController {
    ArrayList<IEntitySPI> placeableEntities;
    ShopNode shopNode;

    ImageView currentChoice = null;

    @FXML private void selectTower1(MouseEvent e) { if (chooseType(Type.TOWER_1)) currentChoice = tower1Frame;}
    @FXML private void selectTower2(MouseEvent e) { if (chooseType(Type.TOWER_2)) currentChoice = tower2Frame;}
    @FXML private void selectTower3(MouseEvent e) { if (chooseType(Type.TOWER_3)) currentChoice = tower3Frame;}
    @FXML private void selectWall(MouseEvent e)    { if (chooseType(Type.STONE_WALL)) currentChoice = wallFrame;}
    @FXML private void selectFence(MouseEvent e) { if (chooseType(Type.WOODEN_FENCE)) currentChoice = fenceFrame;}
    @FXML private void selectRemove(MouseEvent e) {
        GameData.setMouseMode(MouseMode.REMOVING);
        currentChoice = removeFrame;
        GameData.setCurrentlyPlacing(null);
        Engine.removeEntity(GameData.getHand());
        GameData.setHand(null);
    }
    @FXML private AnchorPane pane;

    @FXML private StackPane tower1Pane, tower2Pane, tower3Pane, wallPane, fencePane, removePane;

    @FXML private ImageView bannerImage;
    @FXML private ImageView tower1Icon, tower2Icon, tower3Icon, wallIcon, fenceIcon, removeIcon, tower1Frame, tower2Frame, tower3Frame, wallFrame, fenceFrame, removeFrame;

    List<ImageView> frames;

    @FXML
    public void initialize() {
        bannerImage.setImage(load("/shop/Shop_Banner.png"));
        tower1Icon.setImage(load("/shop/Tower1.png"));
        tower2Icon.setImage(load("/shop/Tower2.png"));
        tower3Icon.setImage(load("/shop/Tower3.png"));
        wallIcon.setImage(load("/shop/wall.png"));
        fenceIcon.setImage(load("/shop/fence.png"));
        removeIcon.setImage(load("/shop/Cross.png"));
        placeableEntities = getServices(IEntitySPI.class);

        frames = Arrays.asList(tower1Frame, tower2Frame, tower3Frame, wallFrame, fenceFrame, removeFrame);
    }

    private Image load(String path) {
        URL url = getClass().getResource(path);
        if (url != null) {
            return new Image(url.toExternalForm());
        } else {
            System.err.println("Missing image: " + path);
            return null;
        }
    }

    private boolean canAfford(Map<IEntitySPI.Type, Integer> prices) {
        Map<IEntitySPI.Type,Integer> inventoryAmounts = GameData.playerInventory.amounts;
        for(IEntitySPI.Type resource : prices.keySet()){
            if(inventoryAmounts.get(resource)==null || inventoryAmounts.get(resource)<prices.get(resource)){
                System.out.println("Not enough of resource in inventory");
                return false;
            }
        }
        return true;
    }


    /*private boolean debit(Map<IEntitySPI.Type, Integer> prices) {
        if (inventoryNode == null) {
            System.err.println("Inventory node not found. Value is null.");
            return false;
        }

        Map<IEntitySPI.Type,Integer> inventoryAmounts = inventoryNode.inventoryComponent.amounts;
        for(IEntitySPI.Type resource : prices.keySet()){
            if(inventoryAmounts.get(resource)==null || inventoryAmounts.get(resource)<prices.get(resource)){
                System.out.println("Not enough resource in inventory");
                return false;
            }
        }
        for(IEntitySPI.Type resource : prices.keySet()){
            inventoryAmounts.put(resource,inventoryAmounts.get(resource)-prices.get(resource));
        }
        return true;
    }*/

    private boolean chooseType(IEntitySPI.Type type) {
        if (!canAfford(ConfigReader.getItemPrices(type))) return false;
        Engine.removeEntity(GameData.getHand());
        GameData.setHand(null);
        GameData.setCurrentlyPlacing(Engine.getEntitySPI(type));
        GameData.setMouseMode(MouseMode.PLACING);
        return true;
    }

    /*private void purchaseItem(Type item) {
        for (InventoryNode node : Engine.getNodes(InventoryNode.class)) {
            inventoryNode = node;
        }
        for (ShopNode node : Engine.getNodes(ShopNode.class)){
            shopNode = node;
        }

        if(debit(ConfigReader.getItemPrices(item))){
            Entity entity = purchase(item);
            Engine.addEntity(entity);
            GameData.setHand(entity);
            GameData.setMouseMode(MouseMode.PLACING);
        }else{
            System.out.println("Insufficient funds.");
        }
    }*/

    /*private Entity purchase(Type type){
        for(IEntitySPI spi : placeableEntities){
            if(spi.getType() == type){
                return spi.create(Engine.getEntity(shopNode.getEntityID()));
            }
        }
        throw new RuntimeException("No such type in spis");

    }*/

    private <T> ArrayList<T> getServices(Class<T> c) {
        return new ArrayList<>(ServiceLoader.load(c).stream().map(ServiceLoader.Provider::get).toList()) ;
    }
}

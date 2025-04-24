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
import java.util.ArrayList;
import java.util.Map;
import java.util.ServiceLoader;

public class ShopController {
    ArrayList<IEntitySPI> placeableEntities;
    private InventoryNode inventoryNode;
    ShopNode shopNode;

    @FXML private void selectTower1(MouseEvent e) { purchaseItem(Type.TOWER_1); }
    @FXML private void selectTower2(MouseEvent e) { purchaseItem(Type.TOWER_2); }
    @FXML private void selectTower3(MouseEvent e) { purchaseItem(Type.TOWER_3); }
    @FXML private void selectWall(MouseEvent e)    { purchaseItem(Type.STONE_WALL); }
    @FXML private AnchorPane pane;

    @FXML private StackPane tower1Pane, tower2Pane, tower3Pane, wallPane;

    @FXML private ImageView bannerImage;
    @FXML private ImageView tower1Icon, tower2Icon, tower3Icon, wallIcon;

    @FXML
    public void initialize() {
        bannerImage.setImage(load("/shop/Shop_Banner.png"));
        tower1Icon.setImage(load("/shop/Tower1.png"));
        tower2Icon.setImage(load("/shop/Tower2.png"));
        tower3Icon.setImage(load("/shop/Tower3.png"));
        wallIcon.setImage(load("/shop/wall.png"));
        placeableEntities = getServices(IEntitySPI.class);
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



    private boolean debit(Map<IEntitySPI.Type, Integer> prices) {
        if (inventoryNode == null) {
            System.err.println("Inventory node not found. Value is null.");
            return false;
        }

        Map<IEntitySPI.Type,Integer> inventoryAmounts = inventoryNode.inventoryComponent.amounts;
        for(IEntitySPI.Type resource : prices.keySet()){
            if(inventoryAmounts.get(resource)==null || inventoryAmounts.get(resource)<prices.get(resource)){
                System.out.println("No such resource in inventory");
                return false;
            }
        }
        for(IEntitySPI.Type resource : prices.keySet()){
            inventoryAmounts.put(resource,inventoryAmounts.get(resource)-prices.get(resource));
        }
        return true;
    }

    private void purchaseItem(Type item) {
        for (InventoryNode node : Engine.getNodes(InventoryNode.class)) {
            inventoryNode = node;
        }
        for (ShopNode node : Engine.getNodes(ShopNode.class)){
            shopNode = node;
        }

        if(debit(ConfigReader.getItemPrices(item.getValue()))){
            Entity entity = purchase(item);
            Engine.addEntity(entity);
            GameData.setHand(entity);
            GameData.setMouseMode(MouseMode.PLACING);
        }else{
            System.out.println("Insufficient funds.");
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

    private <T> ArrayList<T> getServices(Class<T> c) {
        return new ArrayList<>(ServiceLoader.load(c).stream().map(ServiceLoader.Provider::get).toList()) ;
    }
}

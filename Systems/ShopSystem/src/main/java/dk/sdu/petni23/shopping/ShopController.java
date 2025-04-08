package dk.sdu.petni23.shopping;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.configreader.ConfigReader;
import dk.sdu.petni23.common.enums.GameMode;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.entity.IEntitySPI.Type;
import dk.sdu.petni23.inventorysystem.InventoryNode;
import dk.sdu.petni23.structures.wall.Wall;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Map;

public class ShopController {

    private InventoryNode inventoryNode;

    @FXML private AnchorPane pane;

    // Selection panes
    @FXML private StackPane tower1Pane, tower2Pane, tower3Pane, wallPane;

    // Visuals
    @FXML private ImageView bannerImage;
    @FXML private ImageView tower1Icon, tower2Icon, tower3Icon, wallIcon;

    @FXML
    public void initialize() {
        bannerImage.setImage(load("/shop/Shop_Banner.png"));
        tower1Icon.setImage(load("/shop/Tower1.png"));
        tower2Icon.setImage(load("/shop/Tower2.png"));
        tower3Icon.setImage(load("/shop/Tower3.png"));
        wallIcon.setImage(load("/shop/wall.png"));
    }

    private Image load(String path) {
        URL url = getClass().getResource(path);
        if (url != null) {
            return new Image(url.toExternalForm());
        } else {
            System.err.println("❌ Missing image: " + path);
            return null;
        }
    }

    private boolean debit(Map<IEntitySPI.Type, Integer> prices) {
        if (inventoryNode == null) {
            System.err.println("❌ No inventory node found!");
            return false;
        }

        Map<IEntitySPI.Type, Integer> inventoryAmounts = inventoryNode.inventoryComponent.amounts;
        for (IEntitySPI.Type resource : prices.keySet()) {
            Integer have = inventoryAmounts.getOrDefault(resource, 0);
            Integer cost = prices.get(resource);
            if (have < cost) {
                System.out.println("❌ Not enough " + resource + ": " + have + " / " + cost);
                return false;
            }
        }

        // Deduct
        for (IEntitySPI.Type resource : prices.keySet()) {
            inventoryAmounts.put(resource, inventoryAmounts.get(resource) - prices.get(resource));
        }
        return true;
    }

    private void purchaseItem(ShopItems item) {
        for (InventoryNode node : Engine.getNodes(InventoryNode.class)) {
            inventoryNode = node;
        }

        String itemKey;
        switch (item) {
            case TOWER1:
                itemKey = "tower1";
                break;
            case TOWER2:
                itemKey = "tower2";
                break;
            case TOWER3:
                itemKey = "tower3";
                break;
            case STONE_WALL:
                itemKey = "stoneWall";
                break;
            default:
                itemKey = "";
                break;
        }

        if (itemKey.isEmpty()) return;

        Map<IEntitySPI.Type, Integer> prices = ConfigReader.getItemPrices(itemKey);
        if (prices == null || !debit(prices)) return;

        Entity entity = null;
        switch (item) {
            case STONE_WALL:
                entity = Wall.create(new Vector2D(0, 0));
                break;
            case TOWER1:
                break;
            case TOWER2:
            case TOWER3:
                break;
            default:
                return;
        }

        Engine.addEntity(entity);
        GameData.setHand(entity);
        GameData.setGameMode(GameMode.PLACING);
    }

    // Direct click handlers
    @FXML private void selectTower1(MouseEvent e) { purchaseItem(ShopItems.TOWER1); }
    @FXML private void selectTower2(MouseEvent e) { purchaseItem(ShopItems.TOWER2); }
    @FXML private void selectTower3(MouseEvent e) { purchaseItem(ShopItems.TOWER3); }
    @FXML private void selectWall(MouseEvent e)    { purchaseItem(ShopItems.STONE_WALL); }
}

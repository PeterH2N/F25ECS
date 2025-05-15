package dk.sdu.petni23.inventorysystem;

import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.Map;

public class InventoryController {

    // Icons
    @FXML private ImageView bannerImage;
    @FXML private ImageView meatSlotImage;
    @FXML private ImageView woodSlotImage;
    @FXML private ImageView goldSlotImage;
    @FXML private ImageView stoneSlotImage;

    @FXML private ImageView meatIcon;
    @FXML private ImageView woodIcon;
    @FXML private ImageView goldIcon;
    @FXML private ImageView stoneIcon;

    // Digit containers
    @FXML private HBox meatDigitBox;
    @FXML private HBox woodDigitBox;
    @FXML private HBox goldDigitBox;
    @FXML private HBox stoneDigitBox;

    private final Map<Character, Image> digitImages = new HashMap<>();

    @FXML
    public void initialize() {
        // Load digit images 0–9
        for (char c = '0'; c <= '9'; c++) {
            String path = "/Inventory/Number  (" + c + ").png";
            digitImages.put(c, new Image(getClass().getResourceAsStream(path)));
        }

        // Load icons and slot frames
        bannerImage.setImage(load("/Inventory/Banner.png"));

        meatSlotImage.setImage(load("/Inventory/Item_holder.png"));
        woodSlotImage.setImage(load("/Inventory/Item_holder.png"));
        goldSlotImage.setImage(load("/Inventory/Item_holder.png"));
        stoneSlotImage.setImage(load("/Inventory/Item_holder.png"));

        meatIcon.setImage(load("/Inventory/Inventory_Meat.png"));
        woodIcon.setImage(load("/Inventory/Inventory_Wood.png"));
        goldIcon.setImage(load("/Inventory/Inventory_Gold.png"));
        stoneIcon.setImage(load("/Inventory/Inventory_Stone.png"));
    }

    private Image load(String path) {
        return new Image(getClass().getResourceAsStream(path));
    }

    private void updateDigitBox(HBox box, Integer value) {
        box.getChildren().clear();
        char[] digits = "0".toCharArray();
        if (value != null) digits = String.valueOf(value).toCharArray();
        for (char digit : digits) {
            Image img = digitImages.get(digit);
            if (img != null) {
                ImageView view = new ImageView(img);
                view.setFitWidth(180d / 18);  // scaled from 180 px
                view.setFitHeight(228d / 18); // scaled from 228 px
                box.getChildren().add(view);
            }
        }
    }

    public void updateInventoryValues(Map<IEntitySPI.Type, Integer> inv) {
        updateDigitBox(meatDigitBox, inv.get(IEntitySPI.Type.MEAT));
        updateDigitBox(woodDigitBox, inv.get(IEntitySPI.Type.WOOD));
        updateDigitBox(goldDigitBox, inv.get(IEntitySPI.Type.GOLD));
        updateDigitBox(stoneDigitBox, inv.get(IEntitySPI.Type.STONE));
    }
}

package dk.sdu.petni23.inventorysystem;

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
    @FXML private ImageView meatIcon;
    @FXML private ImageView woodIcon;
    @FXML private ImageView goldIcon;

    // Digit containers
    @FXML private HBox meatDigitBox;
    @FXML private HBox woodDigitBox;
    @FXML private HBox goldDigitBox;

    private final Map<Character, Image> digitImages = new HashMap<>();

    @FXML
    public void initialize() {
        // Load digit images 0–9
        for (char c = '0'; c <= '9'; c++) {
            String path = "/Inventory/Number  (" + c + ").png";
            digitImages.put(c, new Image(getClass().getResourceAsStream(path)));
        }

        // Load icons dynamically using fx:id
        bannerImage.setImage(load("/Inventory/Banner.png"));
        meatSlotImage.setImage(load("/Inventory/Item_holder.png"));
        woodSlotImage.setImage(load("/Inventory/Item_holder.png"));
        goldSlotImage.setImage(load("/Inventory/Item_holder.png"));

        meatIcon.setImage(load("/Inventory/Inventory_Meat.png"));
        woodIcon.setImage(load("/Inventory/Inventory_Wood.png"));
        goldIcon.setImage(load("/Inventory/Inventory_Gold.png"));

        // Example data for testing
        updateInventoryValues(34, 0, 98);
    }

    private Image load(String path) {
        return new Image(getClass().getResourceAsStream(path));
    }

    private void updateDigitBox(HBox box, int value) {
        box.getChildren().clear();
        for (char digit : String.valueOf(value).toCharArray()) {
            Image img = digitImages.get(digit);
            if (img != null) {
                ImageView view = new ImageView(img);
                view.setFitWidth(18);  // scaled from 180 px
                view.setFitHeight(22); // scaled from 228 px
                box.getChildren().add(view);
            }
        }
    }

    public void updateInventoryValues(int meat, int wood, int gold) {
        updateDigitBox(meatDigitBox, meat);
        updateDigitBox(woodDigitBox, wood);
        updateDigitBox(goldDigitBox, gold);
    }
}

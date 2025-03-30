package dk.sdu.petni23.debugnode.ui;

import dk.sdu.petni23.common.GameData;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class DebugWindowController
{
    @FXML
    private CheckBox colliders, hitBoxes, hp, grid, wallet;

    @FXML
    public void initialize() {
        colliders.selectedProperty().bindBidirectional(GameData.debugOptions.showColliders);
        hitBoxes.selectedProperty().bindBidirectional(GameData.debugOptions.showHitBoxes);
        hp.selectedProperty().bindBidirectional(GameData.debugOptions.showHP);
        grid.selectedProperty().bindBidirectional(GameData.debugOptions.showGrid);
        wallet.selectedProperty().bindBidirectional(GameData.debugOptions.showWallet);
    }
}

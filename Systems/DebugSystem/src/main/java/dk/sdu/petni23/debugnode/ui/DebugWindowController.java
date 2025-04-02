package dk.sdu.petni23.debugnode.ui;

import dk.sdu.petni23.common.GameData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public class DebugWindowController
{
    @FXML
    private CheckBox colliders, hitBoxes, hp, grid, wallet;
    @FXML
    private Slider SandThreshold, GrassThreshold, Frequency, MaxIslandRadius, MinIslandRadius, CoastRuggedness;
    @FXML
    private Text SandThresholdValue, GrassThresholdValue, FrequencyValue, MaxIslandRadiusValue, MinIslandRadiusValue, CoastRuggednessValue;
    @FXML
    private Button generateButton;

    @FXML
    public void initialize() {
        colliders.selectedProperty().bindBidirectional(GameData.debugOptions.showColliders);
        hitBoxes.selectedProperty().bindBidirectional(GameData.debugOptions.showHitBoxes);
        hp.selectedProperty().bindBidirectional(GameData.debugOptions.showHP);
        grid.selectedProperty().bindBidirectional(GameData.debugOptions.showGrid);
        wallet.selectedProperty().bindBidirectional(GameData.debugOptions.showWallet);

        // set min and max values for sliders
        SandThreshold.minProperty().set(0);
        SandThreshold.maxProperty().bind(GameData.mapGenOptions.grassThreshold);
        GrassThreshold.maxProperty().set(1);
        GrassThreshold.minProperty().bind(GameData.mapGenOptions.sandThreshold);
        Frequency.minProperty().set(0);
        Frequency.maxProperty().set(0.05);
        MaxIslandRadius.minProperty().bind(GameData.mapGenOptions.minIslandRadius);
        MaxIslandRadius.maxProperty().set((double) GameData.worldSize / 2);
        MinIslandRadius.minProperty().set(1);
        MinIslandRadius.maxProperty().bind(GameData.mapGenOptions.maxIslandRadius);
        CoastRuggedness.minProperty().set(0);
        CoastRuggedness.maxProperty().set(1);

        SandThreshold.valueProperty().set(GameData.mapGenOptions.sandThreshold.get());
        GrassThreshold.valueProperty().set(GameData.mapGenOptions.grassThreshold.get());
        Frequency.valueProperty().set(GameData.mapGenOptions.landFrequency.get());
        MaxIslandRadius.valueProperty().set(GameData.mapGenOptions.maxIslandRadius.get());
        MinIslandRadius.valueProperty().set(GameData.mapGenOptions.minIslandRadius.get());
        CoastRuggedness.valueProperty().set(GameData.mapGenOptions.coastRuggedness.get());

        GameData.mapGenOptions.sandThreshold.bind(SandThreshold.valueProperty());
        GameData.mapGenOptions.grassThreshold.bind(GrassThreshold.valueProperty());
        GameData.mapGenOptions.landFrequency.bind(Frequency.valueProperty());
        GameData.mapGenOptions.maxIslandRadius.bind(MaxIslandRadius.valueProperty());
        GameData.mapGenOptions.minIslandRadius.bind(MinIslandRadius.valueProperty());
        GameData.mapGenOptions.coastRuggedness.bind(CoastRuggedness.valueProperty());

        SandThresholdValue.textProperty().bind(SandThreshold.valueProperty().asString());
        GrassThresholdValue.textProperty().bind(GrassThreshold.valueProperty().asString());
        FrequencyValue.textProperty().bind(Frequency.valueProperty().asString());
        MaxIslandRadiusValue.textProperty().bind(MaxIslandRadius.valueProperty().asString());
        MinIslandRadiusValue.textProperty().bind(MinIslandRadius.valueProperty().asString());
        CoastRuggednessValue.textProperty().bind(CoastRuggedness.valueProperty().asString());

        generateButton.setOnAction(actionEvent -> {
            GameData.world.map.genMap();
        });

    }
}

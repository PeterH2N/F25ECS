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
    private Slider SandThreshold, GrassThreshold, Frequency, islandRadius, islandShapeAmplitude, islandShapeFrequency, coastAmplitude, coastFrequency;
    @FXML
    private Text SandThresholdValue, GrassThresholdValue, FrequencyValue, islandRadiusValue, islandShapeAmplitudeValue, islandShapeFrequencyValue, coastAmplitudeValue, coastFrequencyValue;
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
        islandRadius.minProperty().set((double) GameData.worldSize / 4);
        islandRadius.maxProperty().set((double) GameData.worldSize / 2);
        islandShapeAmplitude.minProperty().set(0);
        islandShapeAmplitude.maxProperty().set((double) GameData.worldSize / 2);
        islandShapeFrequency.minProperty().set(0);
        islandShapeFrequency.maxProperty().set(0.1);
        coastAmplitude.minProperty().set(0);
        coastAmplitude.maxProperty().set(10);
        coastFrequency.minProperty().set(0);
        coastFrequency.maxProperty().set(0.5);

        SandThreshold.valueProperty().bindBidirectional(GameData.mapGenOptions.sandThreshold);
        GrassThreshold.valueProperty().bindBidirectional(GameData.mapGenOptions.grassThreshold);
        Frequency.valueProperty().bindBidirectional(GameData.mapGenOptions.landFrequency);
        islandRadius.valueProperty().bindBidirectional(GameData.mapGenOptions.islandRadius);
        islandShapeAmplitude.valueProperty().bindBidirectional(GameData.mapGenOptions.islandShapeAmplitude);
        islandShapeFrequency.valueProperty().bindBidirectional(GameData.mapGenOptions.islandShapeFrequency);
        coastAmplitude.valueProperty().bindBidirectional(GameData.mapGenOptions.coastAmplitude);
        coastFrequency.valueProperty().bindBidirectional(GameData.mapGenOptions.coastFrequency);

        SandThresholdValue.textProperty().bind(SandThreshold.valueProperty().asString());
        GrassThresholdValue.textProperty().bind(GrassThreshold.valueProperty().asString());
        FrequencyValue.textProperty().bind(Frequency.valueProperty().asString());
        islandRadiusValue.textProperty().bind(islandRadius.valueProperty().asString());
        islandShapeAmplitudeValue.textProperty().bind(islandShapeAmplitude.valueProperty().asString());
        islandShapeFrequencyValue.textProperty().bind(islandShapeFrequency.valueProperty().asString());
        coastAmplitudeValue.textProperty().bind(coastAmplitude.valueProperty().asString());
        coastFrequencyValue.textProperty().bind(coastFrequency.valueProperty().asString());

        generateButton.setOnAction(actionEvent -> {
            GameData.world.map.genMap();
        });

    }
}

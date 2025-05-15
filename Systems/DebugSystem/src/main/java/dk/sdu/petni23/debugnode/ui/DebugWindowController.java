package dk.sdu.petni23.debugnode.ui;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.world.GameWorld;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public class DebugWindowController
{
    @FXML
    private CheckBox colliders, hitBoxes, hp, grid, collisionGrid;
    @FXML
    private Slider SandThreshold, GrassThreshold, Frequency, islandRadius, islandShapeAmplitude, islandShapeFrequency, islandShapeOffset, islandCoastOffset, landOffset, coastAmplitude, coastFrequency, forestThreshold, forestFrequency, forestOffset, forestDensity;
    @FXML
    private Text SandThresholdValue, GrassThresholdValue, FrequencyValue, islandRadiusValue, islandShapeAmplitudeValue, islandShapeFrequencyValue, islandShapeOffsetValue, islandCoastOffsetValue, landOffsetValue, coastAmplitudeValue, coastFrequencyValue, forestThresholdValue, forestFrequencyValue, forestOffsetValue, forestDensityValue;
    @FXML
    private Button generateButton, saveButton, loadButton;

    @FXML
    public void initialize() {
        colliders.selectedProperty().bindBidirectional(GameData.debugOptions.showColliders);
        hitBoxes.selectedProperty().bindBidirectional(GameData.debugOptions.showHitBoxes);
        hp.selectedProperty().bindBidirectional(GameData.debugOptions.showHP);
        grid.selectedProperty().bindBidirectional(GameData.debugOptions.showGrid);
        collisionGrid.selectedProperty().bindBidirectional(GameData.debugOptions.showCollisionGrid);

        // set min and max values for sliders
        SandThreshold.minProperty().set(0);
        SandThreshold.maxProperty().bind(GameData.mapGenOptions.grassThreshold);
        GrassThreshold.maxProperty().set(1);
        GrassThreshold.minProperty().bind(GameData.mapGenOptions.sandThreshold);
        Frequency.minProperty().set(0);
        Frequency.maxProperty().set(0.05);
        landOffset.minProperty().set(0);
        landOffset.maxProperty().set(5000);
        islandRadius.minProperty().set((double) GameData.worldSize / 4);
        islandRadius.maxProperty().set((double) GameData.worldSize / 2);
        islandShapeAmplitude.minProperty().set(0);
        islandShapeAmplitude.maxProperty().set((double) GameData.worldSize / 2);
        islandShapeFrequency.minProperty().set(0);
        islandShapeFrequency.maxProperty().set(0.1);

        forestThreshold.minProperty().set(0);
        forestThreshold.maxProperty().set(1);
        forestFrequency.minProperty().set(0);
        forestFrequency.maxProperty().set(0.05);
        forestDensity.minProperty().set(0);
        forestDensity.maxProperty().set(1);

        coastAmplitude.minProperty().set(0);
        coastAmplitude.maxProperty().set(10);
        coastFrequency.minProperty().set(0);
        coastFrequency.maxProperty().set(0.5);
        islandShapeOffset.minProperty().set(0);
        islandShapeOffset.maxProperty().set(5000);
        islandCoastOffset.minProperty().set(0);
        islandCoastOffset.maxProperty().set(5000);

        SandThreshold.valueProperty().bindBidirectional(GameData.mapGenOptions.sandThreshold);
        GrassThreshold.valueProperty().bindBidirectional(GameData.mapGenOptions.grassThreshold);
        Frequency.valueProperty().bindBidirectional(GameData.mapGenOptions.landFrequency);
        landOffset.valueProperty().bindBidirectional(GameData.mapGenOptions.landOffset);
        islandRadius.valueProperty().bindBidirectional(GameData.mapGenOptions.islandRadius);
        islandShapeAmplitude.valueProperty().bindBidirectional(GameData.mapGenOptions.islandShapeAmplitude);
        islandShapeFrequency.valueProperty().bindBidirectional(GameData.mapGenOptions.islandShapeFrequency);
        islandShapeOffset.valueProperty().bindBidirectional(GameData.mapGenOptions.shapeOffset);
        islandCoastOffset.valueProperty().bind(GameData.mapGenOptions.coastOffset);
        coastAmplitude.valueProperty().bindBidirectional(GameData.mapGenOptions.coastAmplitude);
        coastFrequency.valueProperty().bindBidirectional(GameData.mapGenOptions.coastFrequency);
        forestThreshold.valueProperty().bindBidirectional(GameData.mapGenOptions.forestThreshold);
        forestFrequency.valueProperty().bindBidirectional(GameData.mapGenOptions.forestFrequency);
        forestOffset.valueProperty().bindBidirectional(GameData.mapGenOptions.forestOffset);
        forestDensity.valueProperty().bindBidirectional(GameData.mapGenOptions.forestDensity);

        SandThresholdValue.textProperty().bind(SandThreshold.valueProperty().asString());
        GrassThresholdValue.textProperty().bind(GrassThreshold.valueProperty().asString());
        FrequencyValue.textProperty().bind(Frequency.valueProperty().asString());
        islandRadiusValue.textProperty().bind(islandRadius.valueProperty().asString());
        islandShapeAmplitudeValue.textProperty().bind(islandShapeAmplitude.valueProperty().asString());
        islandShapeFrequencyValue.textProperty().bind(islandShapeFrequency.valueProperty().asString());
        islandShapeOffsetValue.textProperty().bind(islandShapeOffset.valueProperty().asString());
        islandCoastOffsetValue.textProperty().bind(islandCoastOffset.valueProperty().asString());
        landOffsetValue.textProperty().bind(landOffset.valueProperty().asString());
        coastAmplitudeValue.textProperty().bind(coastAmplitude.valueProperty().asString());
        coastFrequencyValue.textProperty().bind(coastFrequency.valueProperty().asString());
        forestThresholdValue.textProperty().bind(forestThreshold.valueProperty().asString());
        forestFrequencyValue.textProperty().bind(forestFrequency.valueProperty().asString());
        forestOffsetValue.textProperty().bind(forestOffset.valueProperty().asString());
        forestDensityValue.textProperty().bind(forestDensity.valueProperty().asString());

        generateButton.setOnAction(actionEvent -> GameData.world.map.genMap());

        saveButton.setOnAction(event -> GameData.world.save());
        loadButton.setOnAction(event -> GameData.world.load());

    }
}

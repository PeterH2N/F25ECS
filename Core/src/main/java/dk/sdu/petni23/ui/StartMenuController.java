package dk.sdu.petni23.ui;

import java.io.IOException;
import java.net.URL;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import dk.sdu.petni23.common.util.Sound;

public class StartMenuController {

    @FXML
    private ImageView startButtonImage, settingsButtonImage, exitButtonImage;

    @FXML
    private ImageView startButtonBackground, settingsButtonBackground, exitButtonBackground;

    @FXML
    private Button startButton, settingsButton, exitButton;

    @FXML
    public void initialize() {

        Font.loadFont(getClass().getResourceAsStream("/Fonts/PatrickHand.ttf"), 14);


        Platform.runLater(() -> {
            settingsButton.toFront();
        });
        // Setup button pressed effect for background and icon
        setupButton(startButton, startButtonImage, startButtonBackground,
                "/Icons/Regular_08.png", "/Icons/Pressed_08.png",
                "/Buttons/Button_Blue_3Slides.png", "/Buttons/Button_Blue_3Slides_Pressed.png");

        setupButton(settingsButton, settingsButtonImage, settingsButtonBackground,
                "/Icons/Regular_02.png", "/Icons/Pressed_02.png",
                "/Buttons/Button_Blue_3Slides.png", "/Buttons/Button_Blue_3Slides_Pressed.png");

        setupButton(exitButton, exitButtonImage, exitButtonBackground,
                "/Icons/Regular_01.png", "/Icons/Pressed_01.png",
                "/Buttons/Button_Red_3Slides.png", "/Buttons/Button_Red_3Slides_Pressed.png");
    }

    private void setupButton(Button button, ImageView iconView, ImageView bgView,
            String defaultIcon, String pressedIcon,
            String defaultBg, String pressedBg) {
        button.setOnMousePressed(e -> {
            UISound.play("click1");
            updateImage(iconView, pressedIcon);
            updateImage(bgView, pressedBg);
        });

        button.setOnMouseReleased(e -> {
            updateImage(iconView, defaultIcon);
            updateImage(bgView, defaultBg);
        });
    }

    private void updateImage(ImageView imageView, String imagePath) {
        URL imageURL = getClass().getResource(imagePath);
        if (imageURL != null) {
            imageView.setImage(new Image(imageURL.toExternalForm()));
        } else {
            System.err.println("❌ Image not found: " + imagePath);
        }
    }

    @FXML
    private void startGame() {
        try {
            new dk.sdu.petni23.main.Main().start(new Stage()); // Start the game
            ((Stage) startButton.getScene().getWindow()).close(); // Close menu
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void exitGame() {
        System.exit(0); // Close application
    }

    @FXML
    private void openSettings() {
        try {
            // Load Settings.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Settings.fxml"));
            Parent settingsPanel = loader.load();

            // Get the current scene and replace the root
            Stage stage = (Stage) startButtonBackground.getScene().getWindow();
            stage.getScene().setRoot(settingsPanel);
        } catch (IOException e) {
            System.err.println("❌ ERROR: Could not load Settings.fxml");
            e.printStackTrace();
        }
    }

}

package dk.sdu.petni23.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import dk.sdu.petni23.common.util.Sound;

public class SettingsController {

    @FXML
    private Button setting_exitButton, music_minusButton, music_plusButton, effects_minusButton, effects_plusButton;

    @FXML
    private ImageView setting_exitButtonImage, setting_exitButtonBackground,
            music_minusImage, music_plusImage, effects_minusImage, effects_plusImage,
            music_minusBackground, music_plusBackground, effects_minusBackground, effects_plusBackground,
            music_speakerIcon, music_numberIcon, effects_speakerIcon, effects_numberIcon;

    @FXML
    public void initialize() {
        // Setup button pressed effect for all buttons
        setupButton(setting_exitButton, setting_exitButtonImage, setting_exitButtonBackground,
                "/Icons/Regular_01.png", "/Icons/Pressed_01.png",
                "/Buttons/Button_Red.png", "/Buttons/Button_Red_Pressed.png");

        setupButton(music_minusButton, music_minusImage, music_minusBackground,
                "/Icons/Regular_09.png", "/Icons/Pressed_09.png",
                "/Buttons/Button_Blue.png", "/Buttons/Button_Blue_Pressed.png");

        setupButton(music_plusButton, music_plusImage, music_plusBackground,
                "/Icons/Regular_08.png", "/Icons/Pressed_08.png",
                "/Buttons/Button_Blue.png", "/Buttons/Button_Blue_Pressed.png");

        setupButton(effects_minusButton, effects_minusImage, effects_minusBackground,
                "/Icons/Regular_09.png", "/Icons/Pressed_09.png",
                "/Buttons/Button_Blue.png", "/Buttons/Button_Blue_Pressed.png");

        setupButton(effects_plusButton, effects_plusImage, effects_plusBackground,
                "/Icons/Regular_08.png", "/Icons/Pressed_08.png",
                "/Buttons/Button_Blue.png", "/Buttons/Button_Blue_Pressed.png");
    }

    private void setupButton(Button button, ImageView iconView, ImageView bgView,
            String defaultIcon, String pressedIcon,
            String defaultBg, String pressedBg) {
        if (button == null || iconView == null || bgView == null) {
            System.err.println("❌ setupButton failed: One or more components are null.");
            return;
        }

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
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            imageView.setImage(new Image(imageUrl.toExternalForm()));
        } else {
            System.err.println("❌ Image not found: " + imagePath);
        }
    }

    @FXML
    private void closeSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartMenu.fxml")); // Change to your main panel
            Parent mainPanel = loader.load();

            // Get the current stage and replace settings with the main panel
            Stage stage = (Stage) setting_exitButton.getScene().getWindow();
            stage.getScene().setRoot(mainPanel);
        } catch (IOException e) {
            System.err.println("❌ ERROR: Could not load MainMenu.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    private void decreaseMusicVolume() {
        System.out.println("🔉 Music volume decreased");
        // Add actual logic to decrease music volume here
    }

    @FXML
    private void increaseMusicVolume() {
        System.out.println("🔊 Music volume increased");
        // Add actual logic to increase music volume here
    }

    @FXML
    private void decreaseEffectsVolume() {
        System.out.println("🔉 Effects volume decreased");
        // Add actual logic to decrease effects volume here
    }

    @FXML
    private void increaseEffectsVolume() {
        System.out.println("🔊 Effects volume increased");
        // Add actual logic to increase effects volume here
    }
}

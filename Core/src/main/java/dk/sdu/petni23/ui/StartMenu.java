package dk.sdu.petni23.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class StartMenu extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL fxmlLocation = getClass().getResource("/StartMenu.fxml");

        if (fxmlLocation == null) {
            System.out.println("🚨 FXML file NOT FOUND: ui/StartMenu.fxml");
            System.out.println("🔎 Listing all resources in classpath:");

            try {
                Enumeration<URL> resources = getClass().getClassLoader().getResources("");
                while (resources.hasMoreElements()) {
                    System.out.println("📂 " + resources.nextElement());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            throw new IOException("Cannot load FXML file: ui/StartMenu.fxml");
        } else {
            System.out.println("✅ FXML file found at: " + fxmlLocation);
        }

        Parent root = FXMLLoader.load(fxmlLocation);
        Scene scene = new Scene(root, 600, 400);
        

        // Set custom cursor
        Image cursorImage = new Image(getClass().getResourceAsStream("/Pointers/01.png"), 64, 64, true, true);
        scene.setCursor(new ImageCursor(cursorImage));

        primaryStage.setTitle("F25 - Start Menu");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(false);
        primaryStage.setMaximized(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

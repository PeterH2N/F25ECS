package dk.sdu.petni23.common.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class FXMLUtils {
    public static void switchTo(String fxmlPath, Stage stage, Class<?> contextClass) {
        try {
            URL fxmlLocation = contextClass.getResource(fxmlPath);

            if (fxmlLocation == null) {
                System.err.println("❌ FXML file NOT FOUND: " + fxmlPath);
                return;
            }

            System.out.println("✅ FXML loaded from: " + fxmlLocation);

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

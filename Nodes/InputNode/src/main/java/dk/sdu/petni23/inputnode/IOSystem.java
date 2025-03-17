package dk.sdu.petni23.inputnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.services.IProcessingSystem;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.Scene;

public class IOSystem implements IProcessingSystem
{
    private static final double minZoom = 4;
    private static final double maxZoom = 50;
    private static final double scrollMagnitude = -1.0 / 20.0;
    @Override
    public void update(double deltaTime)
    {
        // camera
        zoom();
        updateCameraPos();

        GameData.gameKeys.update();
    }

    void zoom() {
        double deltaY = GameData.gameKeys.getScrollDeltaY();
        if (deltaY == 0) return;

        double newWidth = GameData.camera.getWidth() + deltaY * scrollMagnitude;
        newWidth = Math.clamp(newWidth, minZoom, maxZoom);
        GameData.camera.setWidth(newWidth);
    }

    void updateCameraPos() {
        if (GameData.camera.following != null) {
            Vector2D pos = new Vector2D(GameData.camera.following.getPosition());
            // cut off at edges of world
            double hws = (double) GameData.worldSize / 2;
            double hw = GameData.camera.getWidth() * 0.5;
            double hh = GameData.camera.getHeight() * 0.5;
            pos.x = Math.clamp(pos.x, -hws + hw, hws - hw);
            pos.y = Math.clamp(pos.y, -hws + hh , hws - hh);
            GameData.camera.setCenter(pos);
        }
    }
}

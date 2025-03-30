package dk.sdu.petni23.inputnode;

import dk.sdu.petni23.common.GameData;

import dk.sdu.petni23.gameengine.util.Vector2D;
import dk.sdu.petni23.gameengine.services.ISystem;

public class IOSystem implements ISystem
{
    private static final double minZoom = 4;
    private static final double maxZoom = GameData.worldSize;
    private static final double scrollMagnitude = -1.0 / 20.0;
    @Override
    public void update(double deltaTime)
    {
        // camera
        zoom();
        updateCameraPos();

        GameData.gameKeys.update();
    }

    @Override
    public int getPriority()
    {
        return Priority.POSTPROCESSING.get();
    }

    void zoom() {
        double deltaY = GameData.gameKeys.getScrollDeltaY();
        double newWidth = GameData.camera.getWidth() + deltaY * scrollMagnitude;
        newWidth = Math.clamp(newWidth, minZoom, maxZoom);
        GameData.camera.setWidth(newWidth);

        double newHeight = GameData.camera.getHeight();
        newHeight = Math.clamp(newHeight, minZoom, maxZoom);
        GameData.camera.setHeight(newHeight);
    }

    void updateCameraPos() {
        if (GameData.camera.following != null) {
            Vector2D pos = new Vector2D(GameData.camera.following.position);
            // cut off at edges of world
            double hws = (double) GameData.worldSize / 2;
            double hw = GameData.camera.getWidth() * 0.5 - 0.001;    //slack
            double hh = GameData.camera.getHeight() * 0.5 - 0.001;   //slack
            pos.x = Math.clamp(pos.x, -hws + hw, hws - hw);
            pos.y = Math.clamp(pos.y, -hws + hh , hws - hh);
            GameData.camera.setCenter(pos);
        }
    }
}

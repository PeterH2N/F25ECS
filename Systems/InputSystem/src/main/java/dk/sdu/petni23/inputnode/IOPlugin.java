package dk.sdu.petni23.inputnode;

import dk.sdu.petni23.common.GameData;

import dk.sdu.petni23.gameengine.services.IPluginService;
import javafx.scene.Camera;
import javafx.scene.Scene;

public class IOPlugin implements IPluginService
{
    @Override
    public void start()
    {
        Scene scene = GameData.scene;
        // Update key bindings for movement
        scene.setOnKeyPressed(event -> GameData.gameKeys.setInput(event.getCode(), true));
        scene.setOnKeyReleased(event -> GameData.gameKeys.setInput(event.getCode(), false));

        // update mouse click
        GameData.canvas.setOnMousePressed(event -> GameData.gameKeys.setInput(event.getButton(), true));
        GameData.canvas.setOnMouseReleased(event -> GameData.gameKeys.setInput(event.getButton(), false));

        // update mouse position in screen space
        scene.setOnMouseMoved(event -> GameData.gameKeys.setMousePos(event.getSceneX(), event.getSceneY()));
        scene.setOnMouseDragged(event -> GameData.gameKeys.setMousePos(event.getSceneX(), event.getSceneY()));

        // get scroll variable
        scene.setOnScroll(scrollEvent -> GameData.gameKeys.setScrollDeltaY(scrollEvent.getDeltaY()));
    }

    @Override
    public void stop()
    {

    }
}

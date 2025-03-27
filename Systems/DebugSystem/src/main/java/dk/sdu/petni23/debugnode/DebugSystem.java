package dk.sdu.petni23.debugnode;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.DebugOptions;
import dk.sdu.petni23.gameengine.services.ISystem;
import javafx.scene.input.KeyCode;

public class DebugSystem implements ISystem
{
    @Override
    public void update(double deltaTime)
    {
        DebugOptions options = GameData.debugOptions;
        // toggle mode
        if (GameData.gameKeys.isDown(KeyCode.CONTROL) && GameData.gameKeys.isPressed(KeyCode.D))
            options.active = !options.active;

    }

    @Override
    public int getPriority()
    {
        return Priority.PREPROCESSING.get();
    }


}



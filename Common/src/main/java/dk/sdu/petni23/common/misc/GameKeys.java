package dk.sdu.petni23.common.misc;


import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.util.HashMap;
import java.util.Map;

public class GameKeys
{
    private static final KeyCode[] keyCodes = KeyCode.values();
    private static final MouseButton[] mouseButtons = MouseButton.values();
    private static final Map<Enum, Boolean> inputs = new HashMap<>();
    private static final Map<Enum, Boolean> pinputs = new HashMap<>();
    private final Vector2D mousePos = new Vector2D(0,0);
    private double scrollDeltaY = 0;

    public GameKeys() {
        for (KeyCode keyCode : keyCodes) {
            inputs.put(keyCode, false);
            pinputs.put(keyCode, false);
        }
        for (MouseButton mouseButton : mouseButtons) {
            inputs.put(mouseButton, false);
            pinputs.put(mouseButton, false);
        }
    }

    public void update() {
        for (KeyCode keyCode : keyCodes) {
            pinputs.put(keyCode, inputs.get(keyCode));
        }
        for (MouseButton mouseButton : mouseButtons) {
            pinputs.put(mouseButton, inputs.get(mouseButton));
        }
        scrollDeltaY = 0;
    }

    public void setInput(Enum k, boolean b) {
        inputs.put(k, b);
    }

    public boolean isDown(Enum k) {
        return inputs.get(k);
    }

    public boolean isPressed(Enum k) {
        return inputs.get(k) && !pinputs.get(k);
    }

    public boolean isReleased(Enum k) {
        return !inputs.get(k) && pinputs.get(k);
    }

    public Vector2D getMousePos() {
        return mousePos;
    }

    public void setMousePos(double x, double y) {
        mousePos.set(x, y);
    }

    public void setMousePos(Vector2D v) {
        mousePos.set(v);
    }

    public void setScrollDeltaY(double y) {
        scrollDeltaY = y;
    }

    public double getScrollDeltaY() {
        return scrollDeltaY;
    }

}

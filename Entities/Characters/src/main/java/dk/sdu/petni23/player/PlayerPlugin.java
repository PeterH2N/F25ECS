package dk.sdu.petni23.player;



import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.ControlComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.health.HealthBarComponent;
import dk.sdu.petni23.common.components.inventory.InventoryComponent;
import dk.sdu.petni23.common.components.inventory.PickUpComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class PlayerPlugin implements IPluginService
{
    @Override
    public void start()
    {
        var player = Knight.create(new Vector2D(0,0));
        var control = new ControlComponent();
        control.ULDR = new KeyCode[] { KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D };
        control.pointsToMouse = true;
        player.add(control);
        player.add(new LayerComponent(LayerComponent.Layer.PLAYER));
        var pickup = player.add(new PickUpComponent());
        pickup.range = 1.5;
        player.add(new InventoryComponent());
        Engine.addEntity(player);
        player.add(new HealthBarComponent(40, 5, Color.GREEN, 1.4));

        // set the camera to track the player
        GameData.camera.following = player.get(PositionComponent.class);
    }

    @Override
    public void stop()
    {

    }
}

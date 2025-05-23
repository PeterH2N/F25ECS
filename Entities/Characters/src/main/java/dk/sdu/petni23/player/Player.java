package dk.sdu.petni23.player;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.ControlComponent;
import dk.sdu.petni23.common.components.RespawnComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.health.HealthBarComponent;
import dk.sdu.petni23.common.components.health.HealthComponent;
import dk.sdu.petni23.common.components.inventory.InventoryComponent;
import dk.sdu.petni23.common.components.inventory.PickUpComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.shop.ShopComponent;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Player implements IPluginService, IEntitySPI {
    @Override
    public void start() {
        Engine.addEntity(create(null));
    }

    @Override
    public void stop() {

    }

    @Override
    public Entity create(Entity parent) {
        var player = Knight.create(new Vector2D(0, 0), Type.PLAYER);
        var control = new ControlComponent();
        control.ULDR = new KeyCode[] { KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D };
        control.pointsToMouse = true;
        player.add(control);
        player.add(new LayerComponent(LayerComponent.Layer.PLAYER));
        var pickup = player.add(new PickUpComponent());
        pickup.range = 1.5;
        player.add(new InventoryComponent(999, Type.GOLD, Type.WOOD, Type.STONE, Type.MEAT));
        player.add(new HealthBarComponent(40, 5, Color.GREEN, 1.4));
        player.add(new ShopComponent());

        // set the camera to track the player
        GameData.camera.following = player.get(PositionComponent.class);

        var respawn = new RespawnComponent();
        respawn.spawnPosition = player.get(PositionComponent.class).position;
        player.add(respawn);

        var health = player.get(HealthComponent.class);

        health.onDeath = node -> {
            var entity = Engine.getEntity(node.getEntityID());
            if (entity.getType() == IEntitySPI.Type.PLAYER) {
                var pos = entity.get(PositionComponent.class);
                if (pos != null) {
                    GameData.pendingRespawns.add(new GameData.RespawnRequest(
                            IEntitySPI.Type.PLAYER,
                            pos.position.clone(),
                            10f));
                    System.out.println("☠ Player died, respawn scheduled");
                }
            }
        };

        return player;
    }

    @Override
    public Type getType() {
        return Type.PLAYER;
    }
}

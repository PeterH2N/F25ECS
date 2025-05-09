package dk.sdu.petni23.structures.workerHut;

import dk.sdu.petni23.common.components.Binding;
import dk.sdu.petni23.common.components.BindingComponent;
import dk.sdu.petni23.common.components.PlacementComponent;
import dk.sdu.petni23.common.components.ai.WorkerComponent;
import dk.sdu.petni23.common.components.collision.CollisionComponent;
import dk.sdu.petni23.common.components.inventory.InventoryComponent;
import dk.sdu.petni23.common.components.inventory.PickUpComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;

import java.util.Objects;

public class WorkerHut implements IEntitySPI {

    private static final SpriteSheet spriteSheet;

    static{
        final int[] numFrames = { 4 };
        Image img = new Image(Objects.requireNonNull(WorkerHut.class.getResourceAsStream("/structuresprites/Wood_Tower_Blue.png")));
        spriteSheet = new SpriteSheet(img,numFrames,new Vector2D(img.getWidth() / 4,img.getHeight()));
    }

    public static Entity workerHut(Vector2D pos) {
        var hut = new Entity(IEntitySPI.Type.WORKER_HUT);
        var positionComponent = hut.add(new PositionComponent(pos));
        hut.add(new SpriteComponent(spriteSheet, new Vector2D(-0.5, -0.9)));
        hut.add(new DisplayComponent(DisplayComponent.Layer.FOREGROUND));
        hut.add(new AnimationComponent());
        hut.add(new InventoryComponent(999, Type.STONE));
        hut.add(new PickUpComponent());

        var rect = new AABBShape((21d * 0.5) / 8, (9d * 0.5) / 8);
        var collision = new CollisionComponent(rect, new Vector2D(0, 0.45));
        collision.active = false;
        hut.add(collision);



        //worker
        var worker = Objects.requireNonNull(Engine.getEntitySPI(Type.WORKER)).create(null);
        Binding binding = (hutE, workerE) -> {
            workerE.get(WorkerComponent.class).home.set(positionComponent.position);
        };
        var bindingComponent = new BindingComponent();
        bindingComponent.bindings.put(worker, binding);

        // not adding binding util hut is places.
        var placementComponent = hut.add(new PlacementComponent(bindingComponent));
        placementComponent.onPlace = (node) -> {
            worker.get(PositionComponent.class).position.set(positionComponent.position.getAdded(0,-1.5));
        };
        // band-aid fix for problem in placement system
        hut.add(new VelocityComponent());
        placementComponent.toRemove.add(VelocityComponent.class);

        return hut;
    }

    @Override
    public Entity create(Entity parent) {
        return workerHut(Vector2D.ZERO);
    }

    @Override
    public Type getType() {
        return Type.WORKER_HUT;
    }
}

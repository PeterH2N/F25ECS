package dk.sdu.petni23.player;

import dk.sdu.petni23.character.Character;
import dk.sdu.petni23.common.components.actions.Action;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.damage.ThrowComponent;
import dk.sdu.petni23.common.components.health.HealthBarComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Objects;

import static dk.sdu.petni23.common.components.ai.AIComponent.Type.CHARACTER;

public class Archer implements IEntitySPI {
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {6,6,8,8,8,8,8};
        Image img = new Image(Objects.requireNonNull(Knight.class.getResourceAsStream("/playersprites/Archer.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 8, img.getHeight() / 7));
    }
    public static Entity create(Vector2D pos) {
        Entity archer = Character.create(pos, 20, null);

        archer.get(VelocityComponent.class).speed = 4;

        var spriteComponent = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -127d / 192));
        archer.add(spriteComponent);

        archer.add(new LayerComponent(LayerComponent.Layer.ENEMY));

        var throwComponent = archer.add(new ThrowComponent(20));
        throwComponent.min = 0;

        IEntitySPI arrowSPI = Engine.getEntitySPI(IEntitySPI.Type.ARROW);
        var actions = archer.add(new ActionSetComponent());
        var attack = new Action(Action.Directionality.OCT);
        actions.actions.add(attack);
        attack.animationIndex = 2;
        attack.duration = 800;
        attack.delay = 600;
        attack.onDispatch = node -> {
            assert arrowSPI != null;
            Engine.addEntity(arrowSPI.create(Engine.getEntity(node.getEntityID())));
        };
        attack.strength = 1;
        archer.add(new HealthBarComponent(40, 5, Color.RED, 1));

        archer.add(new AIComponent(CHARACTER, List.of(CHARACTER), AIComponent.Priority.CLOSEST));

        return archer;
    }

    @Override
    public Entity create(Entity parent) {
        return create(new Vector2D(0,0));
    }

    @Override
    public Type getType() {
        return null;
    }
}

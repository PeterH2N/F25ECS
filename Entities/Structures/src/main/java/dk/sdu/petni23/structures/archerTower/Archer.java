package dk.sdu.petni23.structures.archerTower;

import dk.sdu.petni23.common.components.actions.Action;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.collision.HitBoxComponent;
import dk.sdu.petni23.common.components.damage.AttackComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.damage.ThrowComponent;
import dk.sdu.petni23.common.components.movement.DirectionComponent;
import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.rendering.AnimationComponent;
import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import javafx.scene.image.Image;
import java.util.List;
import java.util.Objects;

import static dk.sdu.petni23.common.components.ai.AIComponent.Type.CHARACTER;

public class Archer {
    private static final SpriteSheet spriteSheet;

    static {
        final int[] numFrames = {6,6,8,8,8,8,8};
        Image img = new Image(Objects.requireNonNull(Archer.class.getResourceAsStream("/charactersprites/Archer.png")));
        spriteSheet = new SpriteSheet(img, numFrames, new Vector2D(img.getWidth() / 8, img.getHeight() / 7));
    }
    public static Entity create() {
        Entity archer = new Entity();
        var position = new PositionComponent(new Vector2D(-5,0));
        archer.add(position);

        var rect = new AABBShape(0.6, 0.85);
        archer.add(new HitBoxComponent(rect, new Vector2D(0, 0.4)));

        var direction = new DirectionComponent();
        archer.add(direction);

        archer.add(new DisplayComponent(DisplayComponent.Layer.EFFECT));
        archer.add(new AnimationComponent());

        var spriteComponent = new SpriteComponent(spriteSheet, new Vector2D(-0.5, -127d / 192));
        archer.add(spriteComponent);

        archer.add(new LayerComponent(LayerComponent.Layer.PLAYER));

        var throwComponent = archer.add(new ThrowComponent(10));
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
        var attackComponent = archer.add(new AttackComponent(1, 1));
        attackComponent.speed = 1;

        archer.add(new AIComponent(null, List.of(AIComponent.Type.CHARACTER), AIComponent.Priority.CLOSEST));

        return archer;
    }
}

package dk.sdu.petni23.common.components.rendering;

import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Component;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;

import java.util.ArrayList;
import java.util.List;

public class SpriteComponent extends Component
{
    public int column, row;
    public transient SpriteSheet spriteSheet;
    public final transient Vector2D spriteOrigin;
    public boolean rotateWithDirection = false;
    public boolean mirror = false;
    public transient List<ColorAdjust> effects = new ArrayList<>();
    //public transient Effect effect = null;

    public SpriteComponent(SpriteSheet spriteSheet, Vector2D spriteOrigin, int column, int row)
    {
        this.spriteSheet = spriteSheet;
        this.spriteOrigin = spriteOrigin;
        this.column = column;
        this.row = row;
    }

    public SpriteComponent(SpriteSheet spriteSheet, Vector2D spriteOrigin) {
        this(spriteSheet, spriteOrigin, 0, 0);
    }

    public SpriteComponent(Vector2D spriteOrigin) {
        this(null, spriteOrigin, 0, 0);
    }
}

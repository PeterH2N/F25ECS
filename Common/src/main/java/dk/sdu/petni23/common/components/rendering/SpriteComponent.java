package dk.sdu.petni23.common.components.rendering;

import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.gameengine.util.Vector2D;
import dk.sdu.petni23.gameengine.Component;

public class SpriteComponent extends Component
{
    public int column, row;
    public final SpriteSheet spriteSheet;
    public final Vector2D spriteOrigin;
    public boolean rotateWithDirection = false;
    public boolean mirror = false;

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
}

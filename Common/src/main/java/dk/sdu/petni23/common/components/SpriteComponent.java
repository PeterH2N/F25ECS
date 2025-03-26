package dk.sdu.petni23.common.components;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Component;
import javafx.scene.image.Image;

public class SpriteComponent extends Component
{
    public int animationIndex = 0;
    public long time = 0;
    public boolean mirror = false;
    public boolean reverse = false;
    public long createdAt = 0;
    public SpriteSheet spriteSheet;
    public Vector2D spriteOrigin;
    public boolean rotateWithDirection = false;

    public SpriteComponent(SpriteSheet spriteSheet, Vector2D spriteOrigin)
    {
        this.spriteSheet = spriteSheet;
        this.spriteOrigin = spriteOrigin;
        createdAt = GameData.getCurrentMillis();
    }
}

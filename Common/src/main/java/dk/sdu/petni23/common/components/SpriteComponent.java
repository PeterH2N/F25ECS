package dk.sdu.petni23.common.components;

import dk.sdu.petni23.common.spritesystem.SpriteSheet;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.image.Image;

public class SpriteComponent extends Component
{
    public int animationIndex = 0;
    public boolean mirror = false;
    public boolean reverse = false;
    public SpriteSheet spriteSheet;
    public Vector2D spriteOrigin;

    public SpriteComponent(SpriteSheet spriteSheet, Vector2D spriteOrigin)
    {
        this.spriteSheet = spriteSheet;
        this.spriteOrigin = spriteOrigin;
    }

    public Image getSprite(long now) {
        return spriteSheet.getAnimation(animationIndex).getSprite(now, reverse);
    }
}

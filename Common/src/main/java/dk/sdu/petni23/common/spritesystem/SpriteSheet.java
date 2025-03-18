package dk.sdu.petni23.common.spritesystem;

import dk.sdu.petni23.common.util.Util;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.image.Image;

public class SpriteSheet
{
    private Animation[] animations;

    public void init(Image img, int[] numFrames) {
        int maxFrames = Util.largestValue(numFrames);
        int numRows = numFrames.length;
        animations = new Animation[numRows];
        for (int i = 0, yOffset = 0; i < numRows; i++,yOffset++) {
            animations[i] = new Animation(img, yOffset, numFrames[yOffset], maxFrames);
        }
    }

    public Animation getAnimation(int i) {
        return animations[i];
    }
}

package dk.sdu.petni23.common.spritesystem;

import dk.sdu.petni23.common.util.Util;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.image.Image;

public class SpriteSheet
{
    private Animation[] animations;

    public void init(Image img, int[] numFrames, Vector2D spriteSize, int[] order) {
        int numRows = numFrames.length;
        var unordered = new Animation[numRows];
        for (int i = 0, yOffset = 0; i < numRows; i++,yOffset++) {
            unordered[i] = new Animation(img, yOffset, numFrames[yOffset], spriteSize);
        }

        animations = new Animation[numRows];
        // sort, if given order
        if (order == null) {
            animations = unordered;
        } else {
            for (int i : order) {
                animations[i] = unordered[order[i]];
            }
        }
    }

    public void init(Image img, int[] numFrames, Vector2D spriteSize) {
        init(img, numFrames, spriteSize, null);
    }

    public Animation getAnimation(int i) {
        return animations[i];
    }
}

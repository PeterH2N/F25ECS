package dk.sdu.petni23.common.spritesystem;

import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Animation
{
    private Image[] images;
    private int numFrames;
    private final int ms = 100;

    private int lastImageIndex = -1;

    public Animation(Image img, int yOffset, int numFrames, Vector2D spriteSize) {
        this.init(img, yOffset, numFrames, spriteSize);
    }

    private void init(Image img, int yOffset, int numFrames, Vector2D spriteSize) {
        this.numFrames = numFrames;
        images = new Image[numFrames];
        int startY = (int) (yOffset * spriteSize.y);
        int j = 0;
        for (int i = 0; i < numFrames; i++) {
            int startX = (int) (j * spriteSize.x);
            if (startX >= img.getWidth()) {
                startY += (int) spriteSize.y;
                startX = 0;
                j = 0;
            }

            PixelReader pr = img.getPixelReader();
            Image sprite = new WritableImage(pr, startX, startY, (int) spriteSize.x, (int) spriteSize.y);

            images[i] = sprite;
            j++;
        }
    }

    public Image getSprite(long time) {
        return getSprite(time, false);
    }

    public Image getSprite(long time, boolean reverse) {
        int i = (int)((time / ms) % numFrames);
        if (reverse) i = (numFrames - 1) - i;
        lastImageIndex = i;

        return images[i];
    }

    public int getLastImageIndex()
    {
        return lastImageIndex;
    }
}

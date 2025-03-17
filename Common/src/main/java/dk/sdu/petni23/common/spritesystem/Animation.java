package dk.sdu.petni23.common.spritesystem;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Animation
{
    private Image[] images;
    private int numFrames;
    private final int ms = 100;

    public Animation(Image img, int yOffset, int numFrames, int longest, boolean mirror) {
        this.init(img, yOffset, numFrames, longest, mirror);
    }

    public Animation(Image img, int yOffset, int numFrames, int longest) {
        this.init(img, yOffset, numFrames, longest, false);
    }

    private void init(Image img, int yOffset, int numFrames, int longest, boolean mirror) {
        this.numFrames = numFrames;
        int pps = (int)img.widthProperty().doubleValue() / longest;
        images = new Image[numFrames];
        for (int i = 0; i < numFrames; i++) {
            int startX = i * pps;
            int startY = yOffset * pps;
            PixelReader pr = img.getPixelReader();

            Image sprite = new WritableImage(pr, startX, startY, pps, pps);
            /*if (mirror) {
                sprite.setRotationAxis(Rotate.Y_AXIS);
                sprite.setRotate(180);
            }*/

            images[i] = sprite;
        }
    }

    public Image getSprite(long time) {
        return getSprite(time, false);
    }

    public Image getSprite(long time, boolean reverse) {
        int i = (int)((time / ms) % numFrames);
        if (reverse) i = (numFrames - 1) - i;

        return images[i];
    }
}

package dk.sdu.petni23.common.spritesystem;

import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class SpriteSheet
{
    public final Image[][] sheet;

    public final int[] numFramesArray;

    public SpriteSheet(Image img, int[] numFramesArray, int xOffset, Vector2D spriteSize, int[] order) {
        this.numFramesArray = numFramesArray;
        sheet = new Image[numFramesArray.length][];
        init(img, numFramesArray, xOffset, spriteSize, order);
    }

    public SpriteSheet(Image img, int[] numFramesArray, int xOffset, Vector2D spriteSize) {
        this(img, numFramesArray, xOffset, spriteSize, null);
    }

    public SpriteSheet(Image img, int[] numFramesArray, Vector2D spriteSize, int[] order) {
        this(img, numFramesArray, 0, spriteSize, order);
    }

    public SpriteSheet(Image img, int[] numFramesArray, Vector2D spriteSize) {
        this(img, numFramesArray, 0, spriteSize);
    }

    public void init(Image img, int[] numFrames, int xOffset, Vector2D spriteSize, int[] order) {
        int numRows = numFrames.length;
        int pixelsPerY = (int) (img.getHeight() / numRows);

        for (int i = 0; i < numRows; i++) {
            if (order == null)
                sheet[i] = makeRow(img, i, xOffset, pixelsPerY, numFrames[i], spriteSize);
            else
                sheet[i] = makeRow(img, order[i], xOffset, pixelsPerY, numFrames[order[i]], spriteSize);
        }
    }

    private Image[] makeRow(Image img, int yOffset, int xOffset, int pixelsPerY, int numFrames, Vector2D spriteSize) {
        var r = new Image[numFrames];
        int startY = (int) (yOffset * pixelsPerY);
        int j = 0;
        for (int i = 0; i < numFrames; i++) {
            int startX = (int) ((xOffset + j) * spriteSize.x);
            if (startX >= img.getWidth()) {
                startY += (int) spriteSize.y;
                startX = 0;
                j = 0;
            }

            if (startY + spriteSize.y > img.getHeight()) break;

            PixelReader pr = img.getPixelReader();
            Image sprite = new WritableImage(pr, startX, startY, (int) spriteSize.x, (int) spriteSize.y);

            r[i] = sprite;
            j++;
        }
        return r;
    }
}

package dk.sdu.petni23.common.spritesystem;

import dk.sdu.petni23.gameengine.util.Vector2D;
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

        for (int i = 0; i < numRows; i++) {
            if (order == null)
                sheet[i] = makeRow(img, i, xOffset, numFrames[i], spriteSize);
            else
                sheet[i] = makeRow(img, order[i], xOffset, numFrames[order[i]], spriteSize);
        }
    }

    private Image[] makeRow(Image img, int yOffset, int xOffset, int numFrames, Vector2D spriteSize) {
        var r = new Image[numFrames];
        int startY = (int) (yOffset * spriteSize.y);
        int j = 0;
        for (int i = 0; i < numFrames; i++) {
            int startX = (int) ((xOffset + j) * spriteSize.x);
            if (startX >= img.getWidth()) {
                startY += (int) spriteSize.y;
                startX = 0;
                j = 0;
            }

            PixelReader pr = img.getPixelReader();
            Image sprite = new WritableImage(pr, startX, startY, (int) spriteSize.x, (int) spriteSize.y);

            r[i] = sprite;
            j++;
        }
        return r;
    }
}

package dk.sdu.petni23.rendernode;

import dk.sdu.petni23.common.Engine;
import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.services.IProcessingSystem;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

public class RenderSystem implements IProcessingSystem
{
    @Override
    public void update(double deltaTime)
    {
        var gc = GameData.canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0, GameData.getDisplayWidth(), GameData.getDisplayHeight());

        drawGrid(gc);
        drawNodes(gc);
        drawFrameTime(gc);
    }



    void drawFrameTime(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.BLACK);
        DecimalFormat df = new DecimalFormat("#0.000000");
        gc.fillText(df.format((double)GameData.getFrameTime() / 1000000000), 10, 20);
        gc.restore();
    }

    void drawNodes(GraphicsContext gc) {
        gc.save();
        gc.setStroke(Color.BLACK);

        // sort by y value
        List<RenderNode> nodes = Engine.getNodes(RenderNode.class);
        nodes.sort((Comparator.comparingDouble(RenderNode::getZ).reversed()));

        for (var node : nodes) {
            drawNode(gc, node);
        }
        gc.restore();
    }

    void drawNode(GraphicsContext gc, RenderNode node) {
        if (node.spriteComponent == null) return;
        // render sprite
        Vector2D pos = GameData.toScreenSpace(node.positionComponent.getPosition());
        Image sprite = node.spriteComponent.getSprite(GameData.getCurrentMillis());

        double width = sprite.getWidth() * GameData.getTileRatio();
        double height = sprite.getHeight() * GameData.getTileRatio();

        // early return
        if (pos.x < -width || pos.x > GameData.getDisplayWidth() + width ||
                pos.y < -height || pos.y > GameData.getDisplayHeight() + height)
            return;

        Vector2D o = node.spriteComponent.spriteOrigin;
        double x = pos.x + (width * o.x);
        double y = pos.y + (height * o.y);

        if (node.spriteComponent.mirror)
            gc.drawImage(sprite, x + width, y, -width, height);
        else
            gc.drawImage(sprite, x, y, width, height);

    }

    void drawGrid(GraphicsContext gc) {
        gc.save();
        gc.setLineWidth(0.5);
        gc.setStroke(Color.GRAY);
        for (int i = 0; i <= GameData.worldSize; i++) {
            Vector2D start = GameData.toScreenSpace((double) -GameData.worldSize / 2 + i, (double) GameData.worldSize / 2);
            Vector2D end = GameData.toScreenSpace((double) -GameData.worldSize / 2 + i, (double) -GameData.worldSize / 2);
            // early return if outside screen
            if (start.x < 0 || start.x > GameData.getDisplayWidth())
                continue;
            gc.strokeLine(start.x, start.y, end.x, end.y);
        }
        // horizontal lines
        for (int i = 0; i <= GameData.worldSize; i++) {
            Vector2D start = GameData.toScreenSpace((double) -GameData.worldSize / 2, (double) -GameData.worldSize / 2 + i);
            Vector2D end = GameData.toScreenSpace((double) GameData.worldSize / 2, (double) -GameData.worldSize / 2 + i);
            // early return if outside screen
            if (start.y < 0 || start.y > GameData.getDisplayHeight())
                continue;
            gc.strokeLine(start.x, start.y, end.x, end.y);
        }
        gc.restore();
    }
}

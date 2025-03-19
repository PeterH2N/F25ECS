package dk.sdu.petni23.rendernode;


import dk.sdu.petni23.common.GameData;

import dk.sdu.petni23.common.components.LifeTimeComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.ISystem;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

public class RenderSystem implements ISystem, IPluginService
{
    private static final Canvas canvas = new Canvas();
    @Override
    public void start()
    {
        GameData.gameWindow.getChildren().add(canvas);
        canvas.getGraphicsContext2D().setImageSmoothing(false);
        canvas.widthProperty().bind(GameData.displayWidthProperty());
        canvas.heightProperty().bind(GameData.displayHeightProperty());
    }

    @Override
    public void stop() {

    }
    @Override
    public void update(double deltaTime)
    {
        var gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0, GameData.getDisplayWidth(), GameData.getDisplayHeight());

        drawGrid(gc);
        drawNodes(gc);
        drawFrameTime(gc);
    }

    @Override
    public int getPriority()
    {
        return Priority.RENDERING.get();
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

        // sort by y value
        List<RenderNode> nodes = Engine.getNodes(RenderNode.class);
        nodes.sort((Comparator.comparingDouble(RenderNode::getZ).reversed()));

        for (var node : nodes) {
            Vector2D pos = GameData.toScreenSpace(node.positionComponent.getPosition());
            drawSprite(gc, node, pos);
            drawBody(gc, node, pos);
            drawHitBox(gc, node, pos);
            drawHealth(gc, node, pos);
        }

        gc.restore();
    }

    void drawSprite(GraphicsContext gc, RenderNode node, Vector2D pos) {
        // render sprite
        if (node.spriteComponent == null) return;
        Image sprite = node.spriteComponent.getSprite();

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

    void drawBody(GraphicsContext gc, RenderNode node, Vector2D pos) {
        gc.setStroke(Color.YELLOW);
        if (node.bodyComponent == null) return;
        drawShape(gc, node.bodyComponent.getShape(), pos);
    }

    void drawHitBox(GraphicsContext gc, RenderNode node, Vector2D pos) {
        gc.setStroke(Color.RED);
        if (node.hitBoxComponent == null) return;
        Vector2D nPos = new Vector2D(pos);
        nPos.y -= node.hitBoxComponent.yOffset * GameData.getPPM();

        drawShape(gc, node.hitBoxComponent.hitBox, nPos);
    }

    void drawShape(GraphicsContext gc, Shape shape, Vector2D pos) {
        switch (shape) {
            case OvalShape oval -> {
                double width = oval.a * 2 * GameData.getPPM();
                double height = oval.b * 2 * GameData.getPPM();
                double x = pos.x - width * 0.5;
                double y = pos.y - height * 0.5;

                gc.strokeOval(x, y, width, height);
            }
            case AABBShape aabb -> {
                double width = aabb.width * GameData.getPPM();
                double height = aabb.height * GameData.getPPM();
                double x = pos.x - width * 0.5;
                double y = pos.y - height * 0.5;

                gc.strokeRect(x, y, width, height);
            }
            default -> throw new IllegalStateException("Unexpected value: " + shape);
        }
    }

    void drawHealth(GraphicsContext gc, RenderNode node, Vector2D pos) {
        if (node.healthComponent == null) return;
        gc.save();
        gc.setFill(Color.GREEN);
        DecimalFormat df = new DecimalFormat("#0.0");
        gc.setFont(new Font(gc.getFont().getName(), GameData.getPPM() * 0.2));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(df.format(node.healthComponent.health), pos.x, pos.y - 80 * GameData.getTileRatio());
        gc.restore();
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

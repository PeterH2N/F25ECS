package dk.sdu.petni23.rendernode;


import dk.sdu.petni23.common.GameData;

import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.util.DebugOptions;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.gameengine.services.IRenderSystem;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RenderSystem implements IRenderSystem, IPluginService
{
    private final Canvas canvas = GameData.canvas;
    private final ColorAdjust white = new ColorAdjust(0.0, -0.5, 0.5, 0);
    private final Color seaColor = Color.rgb(101,160,168);
    private final Rotate r = new Rotate(0);
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
    public void render()
    {
        var gc = canvas.getGraphicsContext2D();
        gc.setFill(seaColor);
        gc.fillRect(0,0, GameData.getDisplayWidth(), GameData.getDisplayHeight());

        drawNodes(gc);
        drawDebug(gc);
    }

    void drawFrameTime(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.setFont(new Font(gc.getFont().getName(), 12));
        DecimalFormat df = new DecimalFormat("#0.000000");
        gc.fillText(df.format((double)GameData.getFrameTime() / 1000000000), GameData.getDisplayWidth() - 10, 20);
    }

    void drawNodes(GraphicsContext gc) {
        drawSpritesByLayer(gc, DisplayComponent.Layer.BACKGROUND);
        drawMap(gc);
        drawSpritesByLayer(gc, DisplayComponent.Layer.FOREGROUND);
        drawSpritesByLayer(gc, DisplayComponent.Layer.EFFECT);
    }

    void drawSpritesByLayer(GraphicsContext gc, DisplayComponent.Layer layer) {
        // get only the nodes in this layer
        List<RenderNode> nodes = new ArrayList<>(Engine.getNodes(RenderNode.class).stream().filter(node -> node.displayComponent.order == layer).toList());
        // sort by y value
        nodes.sort((Comparator.comparingDouble(RenderNode::getY).reversed()));
        drawSprites(gc, nodes);

    }

    void drawSprites(GraphicsContext gc, List<RenderNode> nodes) {
        for (var node : nodes) {
            Vector2D pos = GameData.toScreenSpace(node.positionComponent.position);
            drawSprite(gc, node, pos);
        }
    }

    void drawSprite(GraphicsContext gc, RenderNode node, Vector2D pos) {
        // render sprite
        if (node.spriteComponent == null) return;
        Image sprite = getSprite(node.spriteComponent);

        double width = sprite.getWidth() * GameData.getTileRatio();
        double height = sprite.getHeight() * GameData.getTileRatio();

        // early return
        if (pos.x < -width || pos.x > GameData.getDisplayWidth() + width ||
                pos.y < -height || pos.y > GameData.getDisplayHeight() + height)
            return;

        Vector2D o = node.spriteComponent.spriteOrigin;
        double x = pos.x + (width * o.x);
        double y = pos.y + (height * o.y);
        // hurt
        if (node.healthComponent != null) {
            if (GameData.getCurrentMillis() < node.healthComponent.lastHurtTime + 200) {
                gc.setEffect(white);
            }
        }
        boolean rotated = false;
        if (node.directionComponent != null && node.spriteComponent.rotateWithDirection) {
            rotated = true;
            Rotate r = new Rotate(-node.directionComponent.dir.angleDegrees(), pos.x, pos.y);
            gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        }

        if (node.spriteComponent.mirror)
            gc.drawImage(sprite, x + width, y, -width, height);
        else
            gc.drawImage(sprite, x, y, width, height);

        gc.setEffect(null);
        if (rotated) {
            gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        }
    }

    void drawDebug(GraphicsContext gc) {
        DebugOptions options = GameData.debugOptions;
        if (!options.active) return;
        if (options.showGrid.get()) drawGrid(gc);

        for (var node : Engine.getNodes(RenderNode.class)) {
            Vector2D pos = GameData.toScreenSpace(node.positionComponent.position);
            if (options.showColliders.get()) drawCollider(gc, node, pos);
            if (options.showHitBoxes.get()) drawHitBox(gc, node, pos);
            if (options.showHP.get()) drawHealth(gc, node, pos);
            if (options.showWallet.get()) drawWallet(gc, node, pos);
            drawAim(gc, node);
            drawDir(gc, node, pos);
        }
        drawFrameTime(gc);
    }

    void drawCollider(GraphicsContext gc, RenderNode node, Vector2D pos) {
        if (node.collisionComponent == null) return;
        gc.setStroke(Color.YELLOW);
        Vector2D nPos = new Vector2D(pos);
        nPos.subtract(node.collisionComponent.offset.getMultiplied(GameData.getPPM()));
        drawShape(gc, node.collisionComponent.getShape(), nPos);
    }

    void drawHitBox(GraphicsContext gc, RenderNode node, Vector2D pos) {
        if (node.hitBoxComponent == null) return;
        gc.setStroke(Color.RED);
        Vector2D nPos = new Vector2D(pos);
        nPos.subtract(node.hitBoxComponent.offset.getMultiplied(GameData.getPPM()));

        drawShape(gc, node.hitBoxComponent.hitBox, nPos);
    }
    void drawWallet(GraphicsContext gc, RenderNode node, Vector2D pos) {
        if (node.walletComponent == null) return;
        gc.setFill(Color.GOLDENROD);
        gc.setFont(new Font(gc.getFont().getName(), GameData.getPPM() * 0.2));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(String.valueOf(node.walletComponent.money), pos.x, pos.y - 100 * GameData.getTileRatio());
    }


    void drawAim(GraphicsContext gc, RenderNode node) {
        if (node.throwComponent == null || node.directionComponent == null) return;
        gc.setStroke(Color.RED);
        Vector2D nPos = GameData.toScreenSpace(node.positionComponent.position.getAdded(node.directionComponent.dir.getMultiplied(node.throwComponent.distance)));
        double s = 32 * GameData.getTileRatio();
        gc.strokeOval(nPos.x - s * 0.5, nPos.y - s * 0.5, s,s);
    }

    void drawDir(GraphicsContext gc, RenderNode node, Vector2D start) {
        if (node.directionComponent == null) return;
        gc.setStroke(Color.RED);
        Vector2D pos = node.positionComponent.position;
        Vector2D end = GameData.toScreenSpace(pos.getAdded(node.directionComponent.dir.getMultiplied(0.25)));
        gc.strokeLine(start.x, start.y, end.x, end.y);
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
        gc.setFill(Color.GREEN);
        DecimalFormat df = new DecimalFormat("#0.0");
        gc.setFont(new Font(gc.getFont().getName(), GameData.getPPM() * 0.2));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(df.format(node.healthComponent.health), pos.x, pos.y - 80 * GameData.getTileRatio());
    }
    void drawGrid(GraphicsContext gc) {
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
        gc.setLineWidth(1.0);
    }

    void drawMap(GraphicsContext gc) {
        Vector2D initialPos = GameData.toScreenSpace(new Vector2D((double) -GameData.worldSize / 2, (double) GameData.worldSize / 2));
        var mapImage = GameData.world.map.mapImages;

        for (int i = 0; i < mapImage.length; i++) {
            for (int j = 0; j < mapImage[i].length; j++) {
                Image img = GameData.world.map.mapImages[i][j];
                double width = img.getWidth() * GameData.getTileRatio();
                double height = img.getHeight() * GameData.getTileRatio();
                double x = initialPos.x + j * width;
                double y = initialPos.y + i * height;
                if (x + width < 0 || x > GameData.getDisplayWidth() || y + height < 0 || y > GameData.getDisplayHeight()) continue;
                gc.drawImage(img, x, y, width, height);
            }
        }
    }

    public Image getSprite(SpriteComponent spc) {
        return spc.spriteSheet.sheet[spc.row][spc.column];
    }
}

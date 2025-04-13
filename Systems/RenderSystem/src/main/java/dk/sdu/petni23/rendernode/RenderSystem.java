package dk.sdu.petni23.rendernode;


import dk.sdu.petni23.common.GameData;

import dk.sdu.petni23.common.components.rendering.DisplayComponent;
import dk.sdu.petni23.common.components.rendering.SpriteComponent;
import dk.sdu.petni23.common.shape.AABBShape;
import dk.sdu.petni23.common.shape.OvalShape;
import dk.sdu.petni23.common.shape.Shape;
import dk.sdu.petni23.common.util.DebugOptions;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.common.world.GameWorld;
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
        for (var node : Engine.getNodes(RenderNode.class)) {
            drawHealthBar(gc, node, GameData.toScreenSpace(node.positionComponent.position));
        }
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
        // effect
        gc.setEffect(node.spriteComponent.effect);

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
        if (options.showCollisionGrid.get()) drawCollisionGrid(gc);

        for (var node : Engine.getNodes(RenderNode.class)) {
            Vector2D pos = GameData.toScreenSpace(node.positionComponent.position);
            if (options.showColliders.get()) drawCollider(gc, node, pos);
            if (options.showHitBoxes.get()) drawHitBox(gc, node, pos);
            if (options.showHP.get()) drawHealth(gc, node, pos);
            drawAim(gc, node);
            drawDir(gc, node, pos);
            drawPathFinding(gc, node, pos);
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


    void drawAim(GraphicsContext gc, RenderNode node) {
        if (node.throwComponent == null || node.directionComponent == null) return;
        gc.setStroke(Color.RED);
        var destination = node.positionComponent.position.getAdded(node.directionComponent.dir.getMultiplied(node.throwComponent.distance));
        Vector2D nPos = GameData.toScreenSpace(destination);
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

    void drawHealthBar(GraphicsContext gc, RenderNode node, Vector2D pos) {
        if (node.healthBarComponent == null) return;
        double r = GameData.getTileRatio();
        int barWidth = node.healthBarComponent.width;
        int barHeight = node.healthBarComponent.height;
        double health = node.healthComponent.health;
        double maxHealth = node.healthComponent.maxHealth;
        gc.setStroke(Color.BLACK);
        gc.strokeRoundRect(pos.x-20*r, pos.y-80*r, barWidth*r, barHeight*r, 3, 5);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRoundRect(pos.x-20*r, pos.y-80*r, barWidth*r, barHeight*r, 3, 5);
        gc.setFill(node.healthBarComponent.color);
        gc.fillRoundRect(pos.x-20*r, pos.y-80*r, health/maxHealth * barWidth*r, barHeight*r, 3, 5);
    }

    void drawPathFinding(GraphicsContext gc, RenderNode node, Vector2D pos) {
        if (node.pathFindingComponent == null) return;
        gc.setStroke(Color.BLUE);
        double s = 64 * GameData.getTileRatio();

        var cells = useVisionLine(GameWorld.toTileSpace(node.pathFindingComponent.path.points.get(0)), GameWorld.toTileSpace(node.pathFindingComponent.path.points.getLast()));
        for (var cell : cells) {
            var color = new Color(0,0,1,0.5);
            cell = GameWorld.toWorldSpace(cell);
            var cellPos = GameData.toScreenSpace(cell);
            gc.setFill(color);
            gc.fillRect(cellPos.x, cellPos.y, s, s);
        }

        for (int i = 0; i < node.pathFindingComponent.path.points.size() - 1; i++) {
            var p0 = node.pathFindingComponent.path.points.get(i);
            var p1 = node.pathFindingComponent.path.points.get(i + 1);
            var sp0 = GameData.toScreenSpace(p0);
            var sp1 = GameData.toScreenSpace(p1);
            gc.strokeLine(sp0.x, sp0.y, sp1.x, sp1.y );

            for (var cell : node.pathFindingComponent.collisionCells) {
                var color = new Color(1,0,0,0.5);
                var cellPos = GameData.toScreenSpace(cell);
                gc.setFill(color);
                gc.fillRect(cellPos.x, cellPos.y, s, s);
            }
        }
    }

    void drawCollisionGrid(GraphicsContext gc) {
        var red = new Color(1,0,0,0.5);
        gc.setFill(red);
        for (int x = 0; x < GameData.worldSize; x++) {
            for (int y = 0; y < GameData.worldSize; y++) {
                Vector2D w = GameWorld.toWorldSpace(x, y);
                Vector2D s = GameData.toScreenSpace(w);
                if (s.x < 0 || s.x > GameData.getDisplayWidth() || s.y < 0 || s.y > GameData.getDisplayHeight())
                    continue;
                if (!GameWorld.collisionGrid[y][x].isEmpty()) {

                    double l = 64 * GameData.getTileRatio();
                    gc.fillRect(s.x, s.y, l, l);
                }
            }
        }
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

    private List<Vector2D> bresenhamsLine(Vector2D start, Vector2D end) {
        List<Vector2D> cells = new ArrayList<>();
        int sx, sy;
        int x0 = (int) start.x, y0 = (int) start.y;
        int x1 = (int) end.x, y1 = (int) end.y;

        int dx = Math.abs(x1 - x0);
        if (x0 < x1) sx = 1;
        else sx = -1;
        int dy = -Math.abs(y1 - y0);
        if (y0 < y1) sy = 1;
        else sy = -1;

        int e = dx + dy;

        while(true) {
            cells.add(new Vector2D(x0, y0));
            if (x0 == x1 && y0 == y1) break;
            int e2 = e+e;
            if (e2 >= dy) {
                if (x0 == x1) break;
                e += dy;
                x0 += sx;
            }
            else if (e2 <= dx) {
                if (y0 == y1) break;
                e += dx;
                y0 += sy;
            }
        }
        return cells;
    }

    private List<Vector2D> useVisionLine(Vector2D p1, Vector2D p2) {
        List<Vector2D> cells = new ArrayList<>();
        int x1 = (int) Math.floor(p1.x), y1 = (int) Math.floor(p1.y), x2 = (int) Math.floor(p2.x), y2 = (int) Math.floor(p2.y);
        int i;
        int yStep, xStep;
        int error;
        int errorPrev;
        int y = y1, x = x1;
        int ddy, ddx;
        int dx = x2 - x1;
        int dy = y2 - y1;
        cells.add(new Vector2D(x1, y1));

        if (dy < 0) {
            yStep = -1;
            dy = -dy;
        } else {
            yStep = 1;
        }
        if (dx < 0) {
            xStep = -1;
            dx = -dx;
        } else {
            xStep = 1;
        }
        ddy = 2*dy;
        ddx = 2*dx;
        if (ddx >= ddy) {
            errorPrev = error = dx;
            for (i = 0; i < dx; i++) {
                x += xStep;
                error += ddy;
                if (error > ddx) {
                    y += yStep;
                    error -= ddx;
                    if (error + errorPrev < ddx)
                        cells.add(new Vector2D(x, y - yStep));
                    else if (error + errorPrev > ddx)
                        cells.add(new Vector2D(x-xStep, y));
                    else {
                        cells.add(new Vector2D(x, y - yStep));
                        cells.add(new Vector2D(x-xStep, y));
                    }
                }
                cells.add(new Vector2D(x, y));
                errorPrev = error;
            }
        } else {
            errorPrev = error = dy;
            for (i=0 ; i < dy ; i++){
                y += yStep;
                error += ddx;
                if (error > ddy){
                    x += xStep;
                    error -= ddy;
                    if (error + errorPrev < ddy)
                        cells.add(new Vector2D(x-xStep, y));
                    else if (error + errorPrev > ddy)
                        cells.add(new Vector2D(x, y - yStep));
                    else{
                        cells.add(new Vector2D(x-xStep, y));
                        cells.add(new Vector2D(x, y - yStep));
                    }
                }
                cells.add(new Vector2D(x, y));
                errorPrev = error;
            }
        }
        return cells;
    }

    private List<Vector2D> bresenhamsLineNew(Vector2D start, Vector2D end) {
        List<Vector2D> cells = new ArrayList<>();
        int sx, sy;
        int x0 = (int) start.x, y0 = (int) start.y+1;
        int x1 = (int) end.x, y1 = (int) end.y;

        if (x0 < x1) sx = 1;
        else sx = -1;
        if (y0 < y1) sy = 1;
        else sy = -1;

        /*if (x1 - x0 > 0) x1++;
        else if (x1 - x0 < 0) x1--;*/

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        if (dy < dx) {
            y1++;
            dy = Math.abs(y1 - y0);
        }

        int e = dx - dy;

        while(true) {
            cells.add(new Vector2D(x0, y0));
            if (x0 == x1 && y0 == y1) break;
            int e2 = 4*e;

            if (dy > dx) {
                if (e2 > -dy) {
                    e -= dy;
                    x0 += sx;
                } else if (e2 < dx) {
                    e += dx;
                    y0 += sy;
                }
            } else {
                if (e2 < dx) {
                    e += dx;
                    y0 += sy;
                } else if (e2 > -dy) {
                    e -= dy;
                    x0 += sx;
                }
            }
        }
        return cells;
    }

    public List<Vector2D> lineNoDiag(Vector2D p0, Vector2D p1) {
        List<Vector2D> cells = new ArrayList<>();
        int x0 = (int) p0.x, y0 = (int) p0.y;
        int x1 = (int) p1.x, y1 = (int) p1.y;
        int xDist =  Math.abs(x1 - x0);
        int yDist = -Math.abs(y1 - y0);
        int xStep = (x0 < x1 ? +1 : -1);
        int yStep = (y0 < y1 ? +1 : -1);
        int error = xDist + yDist;

        cells.add(new Vector2D(x0, y0));

        while (x0 != x1 || y0 != y1) {
            if (2*error - yDist > xDist - 2*error) {
                // horizontal step
                error += yDist;
                x0 += xStep;
            } else {
                // vertical step
                error += xDist;
                y0 += yStep;
            }

            cells.add(new Vector2D(x0, y0));
        }
        return cells;
    }

    public Image getSprite(SpriteComponent spc) {
        return spc.spriteSheet.sheet[spc.row][spc.column];
    }
}

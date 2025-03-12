package dk.sdu.petni23.rendernode;

import dk.sdu.petni23.common.Engine;
import dk.sdu.petni23.common.data.GameData;
import dk.sdu.petni23.common.services.ISystem;
import dk.sdu.petni23.common.util.Vector2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class RenderSystem implements ISystem
{
    private final Canvas canvas = new Canvas();
    @Override
    public void update(double deltaTime)
    {
        var gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0, GameData.getDisplayWidth(), GameData.getDisplayHeight());
        gc.setStroke(Color.WHITE);
        for (var node : Engine.getNodes(RenderNode.class)) {
            double[][] polygon = new double[][]{node.display.getPolygonXs(), node.display.getPolygonYs()};
            double[][] renderPoints = new double[2][polygon[0].length];
            for (int i = 0; i < polygon[0].length; i++) {
                var point = new Vector2D(polygon[0][i], polygon[1][i]);
                point.add(node.position.getPosition());
                var screenPoint = toScreenSpace(point);
                renderPoints[0][i] = screenPoint.x;
                renderPoints[1][i] = screenPoint.y;
            }

            gc.strokePolygon(renderPoints[0], renderPoints[1], renderPoints[0].length);
        }

    }

    @Override
    public void start()
    {
        GameData.gameWindow.getChildren().add(canvas);
        canvas.widthProperty().bind(GameData.gameWindow.widthProperty());
        canvas.heightProperty().bind(GameData.gameWindow.heightProperty());
    }

    Vector2D toScreenSpace(double x, double y) {
        Vector2D origin = new Vector2D((double) GameData.getDisplayWidth() / 2, (double) GameData.getDisplayHeight() / 2);
        return origin.getAdded(new Vector2D(x * GameData.getPPM(), -y * GameData.getPPM()));
    }

    Vector2D toScreenSpace(Vector2D v) {
        return toScreenSpace(v.x, v.y);
    }
}

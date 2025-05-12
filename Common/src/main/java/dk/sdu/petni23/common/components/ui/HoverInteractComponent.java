package dk.sdu.petni23.common.components.ui;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Component;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HoverInteractComponent extends Component {
    public final transient Node hoverNode;
    public final transient Node interactNode;

    public final double w, h;

    public final Vector2D offset;

    public HoverInteractComponent(Node interactNode, double width, double height, Vector2D offset) {
        this.offset = offset;
        w = width;
        h = height;
        Rectangle hover = new Rectangle();
        hover.setWidth(width);
        hover.setHeight(height);
        hover.setFill(Color.TRANSPARENT);
        hoverNode = hover;
        this.interactNode = interactNode;
        interactNode.setVisible(false);

        interactNode.setOnMouseMoved(mouseEvent -> interactNode.setVisible(true));
        interactNode.setOnMouseExited(mouseEvent -> interactNode.setVisible(false));
        hoverNode.setOnMouseMoved(mouseEvent -> this.interactNode.setVisible(true));
        hoverNode.setOnMouseExited(mouseEvent -> {
            if (!this.interactNode.isHover())
                this.interactNode.setVisible(false);
        });
    }
}

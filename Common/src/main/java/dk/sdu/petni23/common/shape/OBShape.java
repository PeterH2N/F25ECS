package dk.sdu.petni23.common.shape;

public class OBShape extends Shape{

    public final Direction direction;
    public final double width, height;
    public OBShape(Direction direction, double width, double height) {
        super(new AABB(width * 0.5, height * 0.5));
        this.direction = direction;
        this.width = width;
        this.height = height;
    }

    public enum Direction {
        HORIZONTAL,
        VERTICAL;
    }
}

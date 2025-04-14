package dk.sdu.petni23.common.components.ai;

import dk.sdu.petni23.common.util.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Path {
    public final List<Node> open = new ArrayList<>();
    public final List<Node> closed = new ArrayList<>();

    public static class Node {
        public Node parent = null;
        public final Vector2D cell;
        public double G = 0;
        public double H = 0;

        public double F() {
            return G + H;
        }

        public Node(Vector2D cell) {
            this.cell = cell;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return cell.equals(node.cell);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cell);
        }
    }
}

package dk.sdu.petni23.common.components.ai;

import dk.sdu.petni23.common.util.Vector2D;

import java.util.*;

public class Path {
    public final PriorityQueue<Node> open = new PriorityQueue<>((o1, o2) -> {
        if (o1.F == o2.F) return 0;
        return o1.F > o2.F ? 1 : -1;
    });
    public final List<Node> closed = new ArrayList<>();

    public static class Node {
        public Node parent = null;
        public final Vector2D cell;
        public double G = 0;
        public double H = 0;

        public double F = 0;

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

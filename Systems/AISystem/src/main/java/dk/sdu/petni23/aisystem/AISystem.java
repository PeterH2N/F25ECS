package dk.sdu.petni23.aisystem;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.ControlComponent;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.ai.AIComponent;
import dk.sdu.petni23.common.components.ai.Path;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.components.movement.VelocityComponent;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.common.world.GameWorld;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;

import java.util.*;

public class AISystem implements ISystem {

    static AINode nexus;
    static final Map<Integer, List<AINode>> nodes = new HashMap<>();
    static final double delay = 1;
    static double elapsedTime = 0;

    static {
        for (var layer : LayerComponent.Layer.values()) {
            nodes.put(layer.value(), new ArrayList<>());
        }
    }
    @Override
    public void update(double deltaTime) {
        elapsedTime += deltaTime;
        if (elapsedTime < delay) return;
        for (var node : Engine.getNodes(AINode.class)) {
            if (node.layerComponent == null) continue;
            if (node.directionComponent == null) continue;
            // if node is controlled, we don't control with AI. player has AIComponent so other AIs recognize it.
            if (Engine.getEntity(node.getEntityID()).get(ControlComponent.class) != null) continue;
            if (node.velocityComponent != null) node.velocityComponent.velocity.set(0,0); // reset movement
            var allOpps = nodes.get(node.layerComponent.layer.opponent());
            List<AINode> opps = null;
            boolean inRange = true;

            // get all nodes that fit the priority type, and are in range. if none exist, move on to next.
            for (var type : node.aiComponent.TargetPriorityList) {
                var targets = getOppsInRange(node, type, allOpps);
                if (targets.isEmpty()) continue;
                opps = targets;
                break;
            }
            inRange = opps != null;

            AINode opp;
            if (inRange) {
                // get closest opp
                opp = getClosestDist(node, opps);
            } else {
                opp = nexus; // the nexus is the default
            }
            if (opp == null) continue;

            double minDist = 0.3;
            boolean isPerformingAction = false;
            // vector between node and opp
            var n = opp.positionComponent.position.getSubtracted(node.positionComponent.position).getNormalized();
            // opp distance is most relevant in terms of the hit box
            Vector2D oppPos = opp.positionComponent.position.getAdded(opp.hitBoxComponent.offset);
            // subtract hit box from distance
            double distOffset = Math.abs(n.x) > Math.abs(n.y) ? opp.hitBoxComponent.hitBox.aabb.hw : opp.hitBoxComponent.hitBox.aabb.hh;
            double dist = node.positionComponent.position.distance(oppPos) - distOffset;

            node.directionComponent.dir.set(n);
            // attacks
            if (node.actionSetComponent != null) {
                // check whether we are currently performing an action
                isPerformingAction = GameData.getCurrentMillis() <= node.actionSetComponent.lastActionTime + node.actionSetComponent.lastAction.duration;
                if (!isPerformingAction) {
                    if (node.throwComponent != null) {
                        minDist = node.throwComponent.range * 0.5;
                        boolean canThrow = true;
                        if (node.velocityComponent != null) {
                            // if too close, move away from opp
                            if (dist < minDist) {
                                node.velocityComponent.velocity.set(n.getMultiplied(-node.velocityComponent.speed));
                                canThrow = false;
                            }
                        }
                        // if within throw range
                        if (canThrow && dist + distOffset <= node.throwComponent.range && dist + distOffset > node.throwComponent.min) {
                            node.throwComponent.distance = dist + distOffset;
                            performAction(node.actionSetComponent, 0);
                        }
                    } else if (node.attackComponent != null) {
                        // if within attack range
                        if (dist <= node.attackComponent.range) {
                            performAction(node.actionSetComponent, 0);
                        }
                    }
                }
            }

            if (node.pathFindingComponent != null && node.velocityComponent != null && node.positionComponent != null) {
                node.pathFindingComponent.keepPath = opp.velocityComponent == null; // if opp is static, we don't continually update path.

                if (!isPerformingAction) {
                    if (dist >= minDist) {
                        if (!node.pathFindingComponent.keepPath || node.pathFindingComponent.path.closed.isEmpty()) {
                            // find new path
                            node.pathFindingComponent.path = new Path();
                            var start = GameWorld.toTileSpace(node.positionComponent.position);
                            var end = GameWorld.toTileSpace(opp.positionComponent.position);
                            var startNode = new Path.Node(start);
                            aStar(startNode, end, node.pathFindingComponent.path);
                        }
                    }

                    if (dist >= minDist) {
                        // move according to path
                        if (!node.pathFindingComponent.path.closed.isEmpty()) {
                            var destination = node.pathFindingComponent.path.closed.getLast();
                            var dest = GameWorld.toWorldSpace(destination.cell);
                            dest.x += 0.5;
                            dest.y -= 0.5;
                            // if following set path
                            if (node.pathFindingComponent.keepPath) {
                                // if at immediate destination, remove destination from path
                                var pos = node.positionComponent.position;
                                if (Math.abs(pos.x - dest.x) < 0.1 && Math.abs(pos.y - dest.y) < 0.1) {
                                    node.pathFindingComponent.path.closed.removeLast();
                                }
                            }


                            var dir = node.positionComponent.position.getSubtracted(dest).getNormalized();
                            node.velocityComponent.velocity.set(dir.getMultiplied(-node.velocityComponent.speed));
                            node.directionComponent.dir.set(dir);
                        }
                    }
                }


            }


        }


    }

    private static ArrayList<AINode> getOppsInRange(AINode node, AIComponent.Type type, List<AINode> opps) {
        var targets = new ArrayList<>(opps);
        targets.removeIf(aiNode -> {
            if (aiNode.aiComponent.type != type) return true;
            var dist = aiNode.positionComponent.position.distance(node.positionComponent.position);
            // this opp is not within range
            return dist > node.aiComponent.range;
        });
        return targets;
    }

    @Override
    public int getPriority() {
        return Priority.PROCESSING.get();
    }

    private AINode getClosestDist(AINode node, List<AINode> opps) {
        double closestDist = GameData.worldSize;
        AINode closest = null;
        for (var opp : opps) {
            double distSQ = node.positionComponent.position.distanceSq(opp.positionComponent.position);
            if (distSQ < closestDist * closestDist) {
                closestDist = Vector2D.sqrt(distSQ);
                closest = opp;
            }
        }
        return closest;
    }

    private void performAction(ActionSetComponent acs, int i) {
        if (GameData.getCurrentMillis() <= acs.lastActionTime + acs.lastAction.duration) return;

        long now = GameData.getCurrentMillis();
        acs.lastAction = acs.actions.get(i);
        acs.lastActionTime = now;
    }

    private void aStar(Path.Node current, Vector2D end, Path path) {
        if (current.cell.equals(end) || path.closed.size() > 400) {
            path.closed.add(current);
            // remove all irrelevant nodes
            path.open.clear();
            // only add chosen path to this list
            List<Path.Node> closed = new ArrayList<>();
            Path.Node node = path.closed.getLast();
            while (node.parent != null) {
                closed.add(node);
                node = node.parent;
            }
            path.closed.clear();
            path.closed.addAll(closed);
            return;
        }
        // add all adjacent squares
        int startX = (int) (current.cell.x - 1);
        int endX = (int) (current.cell.x + 1);
        int startY = (int) (current.cell.y - 1);
        int endY = (int) (current.cell.y + 1);
        if (startX < 0) startX = 0;
        if (startY < 0) startY = 0;
        if (endX >= GameData.worldSize) endX = GameData.worldSize - 1;
        if (endY >= GameData.worldSize) endY = GameData.worldSize - 1;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                var adj = new Path.Node(new Vector2D(x, y));
                adj.parent = current;
                if (adj.cell.equals(current.cell)) continue;
                if (!adj.cell.equals(end) && GameWorld.collisionGrid[(int)adj.cell.y][(int)adj.cell.x].stream().anyMatch(collider -> collider.node.getComponent(VelocityComponent.class) == null)) continue;
                if (path.closed.contains(adj)) continue;

                // if diagonal move
                if (y != current.cell.y && x != current.cell.x)
                    adj.G = current.G + 1.41;
                else adj.G = current.G + 1;

                adj.H = manhattanDist(adj.cell, end);
                adj.F = adj.H + adj.G;

                // if node already exists in list
                var openNode = path.open.stream().filter(node -> node.cell.equals(adj.cell)).findFirst().orElse(null);
                if (openNode == null) {
                    path.open.add(adj);
                } else {
                    if (adj.G < openNode.G) {
                        path.open.remove(openNode);
                        path.open.add(adj);
                    }
                }
            }
        }
        path.closed.add(current);

        // squares in open list are sorted, so we just take the last item
        var bestNode = path.open.poll();

        // go again
        assert bestNode != null;
        aStar(bestNode, end, path);
    }

    // to be used in tile space
    private double manhattanDist(Vector2D start, Vector2D end) {
        double xDist = Math.abs(end.x - start.x);
        double yDist = Math.abs(end.y - start.y);

        double smallest, largest;
        if (xDist > yDist) {
            largest = xDist;
            smallest = yDist;
        } else {
            largest = yDist;
            smallest = xDist;
        }

        return largest - smallest + smallest * 1.41;
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


}

package dk.sdu.petni23.aisystem;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.ControlComponent;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.ai.Path;
import dk.sdu.petni23.common.components.damage.AttackComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.common.world.GameWorld;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.node.Node;
import dk.sdu.petni23.gameengine.services.ISystem;

import java.util.*;

public class AISystem implements ISystem {
    static final Map<Integer, List<AINode>> nodes = new HashMap<>();

    static {
        for (var layer : LayerComponent.Layer.values()) {
            nodes.put(layer.value(), new ArrayList<>());
        }
    }
    @Override
    public void update(double deltaTime) {
        for (var node : Engine.getNodes(AINode.class)) {
            if (node.layerComponent == null) continue;
            if (node.directionComponent == null) continue;
            // if node is controlled, we don't control with AI. player has AIComponent so other AIs recognize it.
            if (Engine.getEntity(node.getEntityID()).get(ControlComponent.class) != null) continue;
            if (node.velocityComponent != null) node.velocityComponent.velocity.set(0,0); // reset movement
            var opps = nodes.get(node.layerComponent.layer.opponent());

            // get all nodes that fit the priority type, if none exist, move on to next.
            for (var type : node.aiComponent.TargetPriorityList) {
                var targets = new ArrayList<>(opps);
                targets.removeIf(aiNode -> aiNode.aiComponent.type != type);
                if (targets.isEmpty()) continue;
                opps = targets;
                break;
            }
            if (opps.isEmpty()) continue;

            // get closest opp
            var opp = getClosestDist(node, opps);
            // vector between node and opp
            var n = opp.positionComponent.position.getSubtracted(node.positionComponent.position).getNormalized();
            // set direction to point to opp
            node.directionComponent.dir.set(n);
            // opp distance is most relevant in terms of the hit box
            Vector2D oppPos = opp.positionComponent.position.getAdded(opp.hitBoxComponent.offset);
            // subtract hit box from distance
            double distOffset = Math.abs(n.x) > Math.abs(n.y) ? opp.hitBoxComponent.hitBox.aabb.hw : opp.hitBoxComponent.hitBox.aabb.hh;
            double dist = node.positionComponent.position.distance(oppPos) - distOffset;

            double minDist = 0.3;
            boolean isPerformingAction = false;


            // if closest opp is within range
            boolean inRange = dist <= node.aiComponent.range;
            if (inRange) {
                // attacks
                if (node.actionSetComponent != null) {
                    // check whether we are currently performing an action
                    isPerformingAction = GameData.getCurrentMillis() <= node.actionSetComponent.lastActionTime + node.actionSetComponent.lastAction.duration;
                    if (!isPerformingAction) {
                        if (node.throwComponent != null) {
                            minDist = node.throwComponent.range * 0.5;
                            boolean canThrow = true;
                            if (node.velocityComponent != null) {
                                if (dist< minDist) {
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
                // if node has velocity

            }
            /*if (node.velocityComponent != null) {

                // walk towards opp
                if (!isPerformingAction) {
                    if (inRange && dist >= minDist)
                        node.velocityComponent.velocity.set(n.getMultiplied(node.velocityComponent.speed));
                    else if (!inRange) {
                        // walk towards nexus
                        var dir = node.positionComponent.position.getNormalized();
                        node.velocityComponent.velocity.set(dir.getMultiplied(-node.velocityComponent.speed));
                        node.directionComponent.dir.set(dir);
                    }
                }

            }*/

            if (node.pathFindingComponent != null) {
                node.pathFindingComponent.path.points.clear();
                node.pathFindingComponent.collisionCells.clear();
                pathFind(node.positionComponent.position, opp.positionComponent.position, node.pathFindingComponent.path, node, opp);
            }




        }


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

    private void pathFind(Vector2D start, Vector2D end, Path path, AINode node, Node opp) {
        start = new Vector2D(start);
        end = new Vector2D(end);
        /*start.x = Math.floor(start.x) + 0.5;
        start.y = Math.floor(start.y) + 0.5;
        end.x = Math.floor(end.x) + 0.5;
        end.y = Math.floor(end.y) + 0.5;*/
        path.points.add(start);
        pathFindRecursive(start, end, path, node, opp);
        path.points.add(end);
    }

    private void pathFindRecursive(Vector2D start, Vector2D end, Path path, AINode node, Node opp) {
        // get list of all grid cells te line intersects
        var cells = useVisionLine(GameWorld.toTileSpace(start), GameWorld.toTileSpace(end));

        for (var cell : cells) {
            var colliders = new ArrayList<>(GameWorld.collisionGrid[(int) cell.y][(int) cell.x]);
            colliders.removeIf(collider -> collider.node.getEntityID() == node.getEntityID() || collider.node.getEntityID() == opp.getEntityID());

            // if this cell has a collision object in it (other than the target)
            if (!colliders.isEmpty()) {
                var worldCell = GameWorld.toWorldSpace(cell);
                //cell = GameWorld.toWorldSpace(cell);
                node.pathFindingComponent.collisionCells.add(new Vector2D(worldCell));
                // if cells list contains cell to the side, we must have entered from the side
                // else, we must have entered from the y-axis
                var checkY = new Vector2D(worldCell);
                worldCell.y -= 0.5;
                worldCell.x += 0.5;
                var dir = worldCell.getSubtracted(start).getNormalized();
                checkY.y += dir.y > 0 ? -1 : 1;
                checkY = GameWorld.toTileSpace(checkY);
                int i = 0;
                if (cells.contains(checkY)) {
                    int step = dir.x > 0 ? 1 : -1;
                    int yStep = dir.y > 0 ? -1 : 1;
                    // we move back on the y-axis
                    worldCell.y += yStep;
                    // we move in the direction we are currently searching, until the cell is not a collision cell
                    while(true) {
                        if (GameWorld.collisionGrid[(int) cell.y][(int) cell.x + i].isEmpty()) {
                            worldCell.x += i;
                            break;
                        }
                        if (cell.x >= GameData.worldSize) return;
                        i += step;
                    }
                } else  {
                    int step = dir.y > 0 ? 1 : -1;
                    int xStep = dir.x > 0 ? -1 : 1;
                    // we move back on the x-axis
                    worldCell.x += xStep;
                    // we move in the direction we are currently searching, until the cell is not a collision cell
                    while(true) {
                        if (GameWorld.collisionGrid[(int) cell.y - i][(int) cell.x].isEmpty()) {
                            worldCell.y += i;
                            break;
                        }
                        if (cell.y <= 0) return;
                        i += step;
                    }
                }
                if (path.points.contains(worldCell)) return; // avoid infinite loop
                path.points.add(worldCell);
                pathFindRecursive(worldCell ,end, path, node, opp);
                return;
            }
        }
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

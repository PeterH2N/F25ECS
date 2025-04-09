package dk.sdu.petni23.aisystem;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.ControlComponent;
import dk.sdu.petni23.common.components.actions.ActionSetComponent;
import dk.sdu.petni23.common.components.damage.AttackComponent;
import dk.sdu.petni23.common.components.damage.LayerComponent;
import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
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
            if (node.velocityComponent != null) {

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
}

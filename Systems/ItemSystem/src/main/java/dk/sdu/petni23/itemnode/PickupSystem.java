package dk.sdu.petni23.itemnode;

import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.gameengine.util.Vector2D;

public class PickupSystem implements ISystem
{
    @Override
    public void update(double deltaTime)
    {
        var pickupNodes = Engine.getNodes(PickUpNode.class);
        for (var item : Engine.getNodes(ItemNode.class)) {
            Vector2D itemPos = item.positionComponent.position;
            double closestDist = Double.MAX_VALUE;
            PickUpNode closest = null;
            for (var pickupNode : pickupNodes) {
                if (item.currencyComponent != null && pickupNode.walletComponent == null) continue;
                if (item.currencyComponent == null && pickupNode.inventoryComponent == null) continue;
                double range = pickupNode.pickUpComponent.range;
                var pickUpPos = pickupNode.positionComponent.position;
                double distSq = itemPos.distanceSq(pickUpPos);
                // if within range
                if (distSq <= range * range) {
                    double dist = Math.sqrt(distSq);
                    if (dist < closestDist){
                        closestDist = dist;
                        closest = pickupNode;
                    }
                }
            }
            if (closest == null) continue;
            if (closestDist < 0.3) {
                pickup(item, closest);
            }
            var closestPos = closest.positionComponent.position;

            Vector2D n = closestPos.getSubtracted(itemPos).getNormalized();
            item.velocityComponent.velocity.set(n.getMultiplied(4 / closestDist * closestDist));
        }
    }

    void pickup(ItemNode item, PickUpNode pickup) {
        if (item.currencyComponent != null) {
            if (pickup.walletComponent != null) {
                pickup.walletComponent.money += item.currencyComponent.value;
                Engine.removeEntity(item.getEntityID());
            }
        }

    }

    @Override
    public int getPriority()
    {
        return Priority.PROCESSING.get();
    }
}

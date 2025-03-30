package dk.sdu.petni23.itemnode;

import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.gameengine.util.Vector2D;

public class PickupSystem implements ISystem
{
    double pickupDistance = 0.3;
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
            if (closestDist < pickupDistance) {
                if (pickup(item, closest) && item.itemComponent.onPickup != null)
                    item.itemComponent.onPickup.dispatch(closest);
            }
            var closestPos = closest.positionComponent.position;

            Vector2D n = closestPos.getSubtracted(itemPos).getNormalized();
            double scalar = 4d / (closestDist - pickupDistance) * (closestDist - pickupDistance);
            item.velocityComponent.velocity.set(n.getMultiplied(scalar));
        }
    }

    boolean pickup(ItemNode item, PickUpNode pickup) {
        if (item.currencyComponent != null) {
            if (pickup.walletComponent != null) {
                pickup.walletComponent.money += item.currencyComponent.value;
                Engine.removeEntity(item.getEntityID());
                return true;
            }
        }
        return false;
    }

    @Override
    public int getPriority()
    {
        return Priority.PROCESSING.get();
    }
}

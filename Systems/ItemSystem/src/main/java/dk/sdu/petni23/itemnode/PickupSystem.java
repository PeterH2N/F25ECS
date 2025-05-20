package dk.sdu.petni23.itemnode;

import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.ISystem;
import dk.sdu.petni23.common.util.Vector2D;

public class PickupSystem extends ISystem {
    double pickupDistance = 0.3;
    @Override
    public void update(double deltaTime)
    {
        var pickupNodes = Engine.getNodes(PickUpNode.class);
        for (var item : Engine.getNodes(ItemNode.class)) {
            Vector2D itemPos = item.positionComponent.position;
            item.velocityComponent.velocity.set(0,0);
            double closestDist = Double.MAX_VALUE;
            PickUpNode closest = null;
            for (var pickupNode : pickupNodes) {
                // can't pick up if inventory is not made to accept this item
                var amount = pickupNode.inventoryComponent.amounts.get(item.itemComponent.itemType);
                if (amount == null || amount >= pickupNode.inventoryComponent.maxAmount) continue;
                double range = pickupNode.pickUpComponent.range;
                var pickUpPos = pickupNode.positionComponent.position;
                double distSq = itemPos.distanceSq(pickUpPos);
                // if within range
                if (distSq <= range * range) {
                    double dist = Vector2D.sqrt(distSq);
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
        if (item.currencyComponent != null && item.itemComponent != null) {
            var type = item.itemComponent.itemType;
            var amount = pickup.inventoryComponent.amounts.get(type);
            if (amount == null) return false;
            if (amount >= pickup.inventoryComponent.maxAmount) amount = pickup.inventoryComponent.maxAmount-1; // hard cap on how many items can be carried
            pickup.inventoryComponent.amounts.put(type, amount + 1);
            item.itemComponent.onPickup.dispatch(pickup);
            Engine.removeEntity(item.getEntityID());
            return true;
        }
        return false;
    }
    

    @Override
    public int getPriority()
    {
        return Priority.PROCESSING.get();
    }
}

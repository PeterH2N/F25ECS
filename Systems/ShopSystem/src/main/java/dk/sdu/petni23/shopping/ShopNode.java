package dk.sdu.petni23.shopping;

import dk.sdu.petni23.common.components.movement.PositionComponent;
import dk.sdu.petni23.common.components.shop.ShopComponent;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.node.Node;

public class ShopNode extends Node{

    public PositionComponent positionComponent;
    public ShopComponent shopComponent;
    public ShopNode(Entity entity) {
        super(entity);
    }

    @Override
    public void onRemove() {

    }

}

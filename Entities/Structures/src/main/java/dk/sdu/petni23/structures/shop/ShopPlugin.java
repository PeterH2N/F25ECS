package dk.sdu.petni23.structures.shop;

import dk.sdu.petni23.common.util.Vector2D;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.Entity;
import dk.sdu.petni23.gameengine.services.IPluginService;

public class ShopPlugin implements IPluginService{

    @Override
    public void start() {
        Entity shop = Shop.create(new Vector2D(50,50));
        Engine.addEntity(shop);
    }

    @Override
    public void stop() {

    }
    
}

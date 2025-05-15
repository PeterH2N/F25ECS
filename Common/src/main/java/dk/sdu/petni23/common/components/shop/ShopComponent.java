package dk.sdu.petni23.common.components.shop;

import java.util.HashMap;
import java.util.Map;

import dk.sdu.petni23.gameengine.Component;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;

public class ShopComponent extends Component{
    public boolean visible = false;
    public Map<IEntitySPI.Type, Integer> stock = new HashMap<>();

    public ShopComponent(){

    }
}

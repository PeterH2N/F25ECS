package dk.sdu.petni23.shopping;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.configreader.ConfigReader;
import dk.sdu.petni23.common.enums.MouseMode;
import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.entity.IEntitySPI.Type;

public class ShopLogic {
    public boolean canAfford(Map<IEntitySPI.Type, Integer> prices,Optional<Map<Type,Integer>> amountsOptional) {
        Map<IEntitySPI.Type,Integer> inventoryAmounts = amountsOptional.orElseGet(() -> GameData.playerInventory.amounts);
        for(IEntitySPI.Type resource : prices.keySet()){
            if(inventoryAmounts.get(resource)==null || inventoryAmounts.get(resource) < prices.get(resource)){
                StringBuilder msg = new StringBuilder("Requires ");
                for (Iterator<Type> it = prices.keySet().iterator(); it.hasNext(); ) {
                    var res = it.next();
                    msg.append(prices.get(res)).append(" ");
                    msg.append(res.name().toLowerCase(Locale.ROOT));
                    if (it.hasNext()) msg.append(", ");
                }
                if(!amountsOptional.isPresent()){
                    GameData.gameLog.write(msg.toString());
                }
                return false;
            }
        }
        return true;
    }
    public boolean chooseType(IEntitySPI.Type type) {
        if (!canAfford(ConfigReader.getItemPrices(type),Optional.empty())) return false;
        Engine.removeEntity(GameData.getHand());
        GameData.setHand(null);
        GameData.setCurrentlyPlacing(Engine.getEntitySPI(type));
        GameData.setMouseMode(MouseMode.PLACING);
        return true;
    }
}

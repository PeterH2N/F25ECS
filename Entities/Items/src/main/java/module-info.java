
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.items.*;

module Items {
    requires GameEngine;
    requires Common;
    requires javafx.graphics;
    provides IEntitySPI with SpawnGoldSPI, SpawnMeatSPI, SpawnWoodSPI, SpawnStoneSPI, GoldSPI, MeatSPI, WoodSPI, StoneSPI;
}
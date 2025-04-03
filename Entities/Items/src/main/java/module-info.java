
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.items.GoldSPI;
import dk.sdu.petni23.items.MeatSPI;
import dk.sdu.petni23.items.WoodSPI;

module Items {
    requires GameEngine;
    requires Common;
    requires javafx.graphics;
    provides IEntitySPI with GoldSPI, MeatSPI, WoodSPI;
}
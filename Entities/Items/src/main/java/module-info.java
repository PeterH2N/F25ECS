
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.items.GoldSPI;
import dk.sdu.petni23.items.MeatSPI;
import dk.sdu.petni23.items.StoneSPI;
import dk.sdu.petni23.items.WoodSPI;
import dk.sdu.petni23.items.StoneSPI;

module Items {
    requires GameEngine;
    requires Common;
    requires javafx.graphics;
    provides IEntitySPI with GoldSPI, MeatSPI, WoodSPI, StoneSPI;

    exports dk.sdu.petni23.items;
}
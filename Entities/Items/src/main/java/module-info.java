
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.items.GoldSPI;

module Items {
    requires GameEngine;
    requires Common;
    requires javafx.graphics;
    provides IEntitySPI with GoldSPI;
}
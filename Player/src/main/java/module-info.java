import dk.sdu.petni23.common.entity.IEntitySPI;
import dk.sdu.petni23.common.services.ISystem;
import dk.sdu.petni23.player.PlayerSPI;
import dk.sdu.petni23.player.PlayerSystem;

module Player {
    requires javafx.graphics;
    requires Common;
    opens dk.sdu.petni23.player to javafx.graphics;
    uses PlayerSPI;
    provides IEntitySPI with PlayerSPI;
    provides ISystem with PlayerSystem;
}
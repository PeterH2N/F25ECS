package dk.sdu.petni23.enemy;

import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.entity.IEntitySPI;
import dk.sdu.petni23.gameengine.services.IPluginService;
import dk.sdu.petni23.player.PlayerSPI;

import java.util.Optional;

public class EnemyPlugin implements IPluginService
{
    @Override
    public void start()
    {
        for (int i = 0; i < 50; i++)
            getSPI().ifPresent(spi -> Engine.addEntity(spi.create()));
    }

    @Override
    public void stop()
    {

    }

    private static Optional<TorchGoblinSPI> getSPI() {
        for (var spi : Engine.getEntitySPIs()) {
            if (spi instanceof TorchGoblinSPI pspi)
                return Optional.of(pspi);
        }
        return Optional.empty();
    }
}

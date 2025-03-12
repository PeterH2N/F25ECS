package dk.sdu.petni23.player;

import dk.sdu.petni23.common.Engine;
import dk.sdu.petni23.common.services.ISystem;

import java.util.Optional;
import java.util.ServiceLoader;

public class PlayerSystem implements ISystem
{
    @Override
    public void update(double deltaTime)
    {

    }

    @Override
    public void start()
    {
        getSPI().ifPresent(spi -> Engine.addEntity(spi.create()));
    }

    private static Optional<PlayerSPI> getSPI() {
        for (var spi : Engine.getEntitySPIs()) {
            if (spi instanceof PlayerSPI pspi)
                return Optional.of(pspi);
        }
        return Optional.empty();
    }
}

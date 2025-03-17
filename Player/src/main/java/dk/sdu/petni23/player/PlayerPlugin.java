package dk.sdu.petni23.player;

import dk.sdu.petni23.common.Engine;
import dk.sdu.petni23.common.entity.Entity;
import dk.sdu.petni23.common.services.IPluginService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerPlugin implements IPluginService
{
    static List<Entity> players = new ArrayList<>();
    @Override
    public void start()
    {
        getSPI().ifPresent(spi -> {
            Entity player = spi.create();
            players.add(player);
            Engine.addEntity(player);
        });
    }

    @Override
    public void stop()
    {
        for (var player : players)
            Engine.removeEntity(player);
    }

    private static Optional<PlayerSPI> getSPI() {
        for (var spi : Engine.getEntitySPIs()) {
            if (spi instanceof PlayerSPI pspi)
                return Optional.of(pspi);
        }
        return Optional.empty();
    }
}

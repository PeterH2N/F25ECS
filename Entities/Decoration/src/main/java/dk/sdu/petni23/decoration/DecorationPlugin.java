package dk.sdu.petni23.decoration;


import dk.sdu.petni23.gameengine.Engine;
import dk.sdu.petni23.gameengine.services.IPluginService;

import java.util.Optional;

public class DecorationPlugin implements IPluginService
{
    final int numDecorations = 100;
    @Override
    public void start()
    {
        getSPI().ifPresent(spi -> {
            for (int i = 0; i < numDecorations; i++) {
                Engine.addEntity(spi.create(null));
            }
        });
    }

    @Override
    public void stop()
    {

    }

    private static Optional<DecorationSPI> getSPI() {
        for (var spi : Engine.getEntitySPIs()) {
            if (spi instanceof DecorationSPI pspi)
                return Optional.of(pspi);
        }
        return Optional.empty();
    }
}

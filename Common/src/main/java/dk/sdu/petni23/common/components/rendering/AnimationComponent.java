package dk.sdu.petni23.common.components.rendering;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.gameengine.Component;

public class AnimationComponent extends Component
{
    public long time = 0;
    public boolean reverse = false;
    public boolean doMirrors = true;
    public long createdAt = GameData.getCurrentMillis();
}

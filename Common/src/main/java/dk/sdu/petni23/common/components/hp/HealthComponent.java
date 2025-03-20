package dk.sdu.petni23.common.components.hp;

import dk.sdu.petni23.common.GameData;
import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.gameengine.Component;

public class HealthComponent extends Component
{
    private double maxHealth;
    private double health;
    private long lastHurtTime = 0;
    public Dispatch onDeath;

    public void setMaxHealth(double max) {
        maxHealth = max;
        setHealth(max);
    }

    public void hurt(double dmg) {
        setHealth(getHealth() - dmg);
        lastHurtTime = GameData.getCurrentMillis();
    }

    public double getMaxHealth()
    {
        return maxHealth;
    }

    public double getHealth()
    {
        return health;
    }

    public void setHealth(double health)
    {
        this.health = health;
    }

    public long getLastHurtTime()
    {
        return lastHurtTime;
    }
}

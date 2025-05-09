package dk.sdu.petni23.common.components.health;

import dk.sdu.petni23.common.components.Dispatch;
import dk.sdu.petni23.gameengine.Component;

public class HealthComponent extends Component
{
    public double maxHealth;
    public double health;
    public long lastHurtTime = 0;
    public transient Dispatch onDeath = null;
    public transient Dispatch onHurt = null;
    public boolean invincible = false;

    public HealthComponent(double max) {
        maxHealth = max;
        health = max;
    }

    public void heal(double amount) {
        health = Math.min(health + amount, maxHealth);
    }
}

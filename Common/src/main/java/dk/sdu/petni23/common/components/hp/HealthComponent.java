package dk.sdu.petni23.common.components.hp;

import dk.sdu.petni23.gameengine.Component;

public class HealthComponent extends Component
{
    public double maxHealth;
    public double health;

    public void setMaxHealth(double max) {
        maxHealth = max;
        health = max;
    }
}

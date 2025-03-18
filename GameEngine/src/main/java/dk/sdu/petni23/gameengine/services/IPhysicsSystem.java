package dk.sdu.petni23.gameengine.services;

public interface IPhysicsSystem
{
    void preUpdate();
    void step(double deltaTime);
}

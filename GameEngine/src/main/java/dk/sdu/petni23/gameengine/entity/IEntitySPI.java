package dk.sdu.petni23.gameengine.entity;

import dk.sdu.petni23.gameengine.node.Node;

public interface IEntitySPI
{
    Entity create(Node parent);

    Type getType();
    enum Type {
        PLAYER,
        ENEMY,
        ENVIRONMENT,
        DAMAGE;
    }
}

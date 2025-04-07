package dk.sdu.petni23.gameengine.entity;

import dk.sdu.petni23.gameengine.node.Node;

public interface IEntitySPI
{
    Entity create(Entity parent);

    Type getType();
    enum Type {
        PLAYER,
        ENEMY,
        ENVIRONMENT,
        DEATH_ANIMATION,
        FOAM_ANIMATION,
        GOLD,
        DAMAGE,
        DYNAMITE,
        MEAT,
        WOOD,
        STONE,
        TREE,
        EXPLOSION_ANIMATION;
    }
}

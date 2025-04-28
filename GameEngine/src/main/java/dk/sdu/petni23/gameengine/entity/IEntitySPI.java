package dk.sdu.petni23.gameengine.entity;

import dk.sdu.petni23.gameengine.node.Node;

public interface IEntitySPI
{
    Entity create(Entity parent);

    Type getType();
    enum Type {
        NONE,
        NEXUS,
        PLAYER,
        ENEMY,
        KNIGHT,
        ARCHER,
        TORCH_GOBLIN,
        TNT_GOBLIN,
        SHEEP,
        ENVIRONMENT,
        DEATH_ANIMATION,
        FOAM_ANIMATION,
        GOLD,
        SPAWN_GOLD,
        DAMAGE,
        DYNAMITE,
        ARROW,
        LANDED_ARROW,
        MEAT,
        SPAWN_MEAT,
        WOOD,
        SPAWN_WOOD,
        STONE,
        SPAWN_STONE,
        TREE,
        EXPLOSION_ANIMATION,
        WOODEN_FENCE,
        STONE_WALL,
        MINE,
        TOWER_1,
        TOWER_2,
        TOWER_3,
        GOBLIN_HOUSE;

        public static Type getTypeFromString(String input){
            for(Type type : Type.values()){
                if(type.toString().equalsIgnoreCase(input)){
                    return type;
                }
            }
            throw new IllegalArgumentException("No such value in Type enum.");
        }
    }
}

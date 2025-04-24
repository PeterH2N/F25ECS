package dk.sdu.petni23.gameengine.entity;

import dk.sdu.petni23.gameengine.node.Node;

public interface IEntitySPI
{
    Entity create(Entity parent);

    Type getType();
    enum Type {
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
        DAMAGE,
        DYNAMITE,
        ARROW,
        MEAT,
        WOOD,
        STONE,
        TREE,
        EXPLOSION_ANIMATION,
        WOODEN_WALL,
        STONE_WALL,
        TOWER_1,
        TOWER_2,
        TOWER_3;

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

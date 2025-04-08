package dk.sdu.petni23.gameengine.entity;

import dk.sdu.petni23.gameengine.node.Node;

public interface IEntitySPI
{
    Entity create(Entity parent);

    Type getType();
    enum Type {
        PLAYER("player"),
        ENEMY("enemy"),
        ENVIRONMENT("environmennt"),
        DEATH_ANIMATION("death_animation"),
        FOAM_ANIMATION("foam_animation"),
        GOLD("gold"),
        DAMAGE("damage"),
        DYNAMITE("dynamite"),
        MEAT("meat"),
        WOOD("wood"),
        STONE("stone"),
        EXPLOSION_ANIMATION("explosion_animation"),
        WOODEN_WALL("wooden_wall"),
        STONE_WALL("stone_wall");

        private final String value;

        public String getValue(){
            return value;
        }

        Type(String value){
            this.value = value;
        }

        public static Type getTypeFromString(String input){
            for(Type type : Type.values()){
                if(type.value.equalsIgnoreCase(input)){
                    return type;
                }
            }
            throw new IllegalArgumentException("No such value in Type enum.");
        }
    }
}

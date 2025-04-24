package dk.sdu.petni23.gameengine.entity;

import dk.sdu.petni23.gameengine.node.Node;

public interface IEntitySPI
{
    Entity create(Entity parent);

    Type getType();
    enum Type {
        PLAYER("player"),
        ENEMY("enemy"),
        ARCHER("archer"),
        ENVIRONMENT("environmennt"),
        DEATH_ANIMATION("death_animation"),
        FOAM_ANIMATION("foam_animation"),
        GOLD("gold"),
        DAMAGE("damage"),
        DYNAMITE("dynamite"),
        ARROW("arrow"),
        MEAT("meat"),
        WOOD("wood"),
        STONE("stone"),
        TREE("tree"),
        EXPLOSION_ANIMATION("explosion_animation"),
        WOODEN_WALL("wooden_wall"),
        STONE_WALL("stone_wall"),
        TOWER_1("tower_1"),
        TOWER_2("tower_2"),
        TOWER_3("tower_3"),
        GOBLIN_HOUSE("goblin_house");

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

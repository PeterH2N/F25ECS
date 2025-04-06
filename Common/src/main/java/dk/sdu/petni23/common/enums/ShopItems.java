package dk.sdu.petni23.common.enums;

public enum ShopItems {
    STONE_WALL(0),
    WOODEN_WALL(1);
    private final int index;
    public int get(){
        return index;
    }
    ShopItems(int index){
        this.index = index;
    }
}

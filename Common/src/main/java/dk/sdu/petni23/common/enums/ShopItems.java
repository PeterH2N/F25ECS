package dk.sdu.petni23.common.enums;

public enum ShopItems {
    TOWER1(0),
    TOWER2(1),
    TOWER3(2),
    STONE_WALL(3);

    private final int index;

    ShopItems(int index) {
        this.index = index;
    }

    public int get() {
        return index;
    }
}

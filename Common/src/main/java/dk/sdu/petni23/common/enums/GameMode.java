package dk.sdu.petni23.common.enums;

public enum GameMode{
    REGULAR(0),
    PLACING(1),
    SHOPPING(2);
    private final int index;
    public int get(){
        return index;
    }
    GameMode(int index){
        this.index = index;
    }
}
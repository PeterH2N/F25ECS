package dk.sdu.petni23.common.enums;

public enum GameMode{
    WAIT(0),
    IN_GAME(1);
    private final int index;
    public int get(){
        return index;
    }
    GameMode(int index){
        this.index = index;
    }
}
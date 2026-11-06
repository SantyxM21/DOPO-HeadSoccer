package domain;

public abstract class Player extends Entity {
    private Character_ selectedCharacter;

    

    public Player(Character_ ch){
        this.selectedCharacter = ch;
        throw new PoobSoccerException("PoobSoccerException.NOT_DATA");
    }

    public void move(){
        throw new PoobSoccerException("PoobSoccerException.NOT_DATA");
    }

    public void kick(){
        throw new PoobSoccerException("PoobSoccerException.NOT_DATA");
    }

    public void jump(){
        throw new PoobSoccerException("PoobSoccerException.NOT_DATA");
    }

    public void dash(){
        throw new PoobSoccerException("PoobSoccerException.NOT_DATA");
    }
}

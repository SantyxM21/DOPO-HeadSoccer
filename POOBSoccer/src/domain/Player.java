package domain;

public abstract class Player extends Entity {
    private Character_ selectedCharacter;

    

    public Player(Character_ ch) throws PoobSoccerException{
        this.selectedCharacter = ch;
        throw new PoobSoccerException(PoobSoccerException.NOT_DATA);
    }

    public void move() throws PoobSoccerException {
        throw new PoobSoccerException(PoobSoccerException.NOT_DATA);
    }

    public void kick() throws PoobSoccerException{
        throw new PoobSoccerException(PoobSoccerException.NOT_DATA);
    }

    public void jump() throws PoobSoccerException{
        throw new PoobSoccerException(PoobSoccerException.NOT_DATA);
    }

    public void dash() throws PoobSoccerException{
        throw new PoobSoccerException(PoobSoccerException.NOT_DATA);
    }

    public void selectCharacter() throws PoobSoccerException{
        throw new PoobSoccerException(PoobSoccerException.NOT_DATA);
    }
}

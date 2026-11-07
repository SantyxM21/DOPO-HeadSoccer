package domain;

public abstract class Player extends Entity {
    private Character_ selectedCharacter;

    

    public Player(Character_ ch) throws DopoSoccerException{
        this.selectedCharacter = ch;
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public void move() throws DopoSoccerException {
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public void kick() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public void jump() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public void dash() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public void selectCharacter() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }
}

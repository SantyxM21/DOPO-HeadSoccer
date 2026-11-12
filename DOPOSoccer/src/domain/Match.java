package domain;

public abstract class Match {
    private Player leftPlayer;
    private Player rightPlayer;
    private Field field;
    private Ball ball;

    public Match(Player leftPlayer, Player rightPlayer, Field field, Ball ball) throws DopoSoccerException{
        this.leftPlayer = leftPlayer;
        this.rightPlayer = rightPlayer;
        this.field = field;
        this.ball = ball;
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public void playerAction(int player, String action) throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public Player getLeftPlayer(){
        return leftPlayer;
    }

    public Player getRightPlayer(){
        return rightPlayer;
    }

    public Field getField(){
        return field;
    }

    public Ball getBall(){
        return ball;
    }
}

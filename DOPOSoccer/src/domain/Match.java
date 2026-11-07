package domain;

public class Match {
    private Player leftPlayer;
    private Player rightPlayer;
    private Field field;
    private Ball ball;

    public Match(Player lfPlayer, Player rgPlayer, Field field, Ball ball) throws DopoSoccerException{
        this.leftPlayer = lfPlayer;
        this.rightPlayer = rgPlayer;
        this.field = field;
        this.ball = ball;
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public Ball getBall(){
        return ball;
    }

    
}
